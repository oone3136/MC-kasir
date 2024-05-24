package com.pembayaran.service;

import com.pembayaran.entity.PembayaranManual;
import com.pembayaran.request.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import com.pembayaran.repository.PMRepository;
import com.pembayaran.status.StatusPembayaran;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class PMCratedService {

    private final PMRepository repository;
    private final WebClient webClienttrxD;
    private static final Logger log = LoggerFactory.getLogger(PMCratedService.class);

    public ResponseEntity<String> createPembayaran(PMCreateRequest request) {
        try {
            log.info("get : {}", request.getPenerima());

            TrxDGetRequest dGetRequest = new TrxDGetRequest();
            dGetRequest.setPemesan(request.getPenerima());

            WebClient webClientWithLogging = webClienttrxD.mutate()
                    .filter(logRequest())
                    .filter(logResponse())
                    .build();

            TRXDetailDao trxDetailDao1 = webClientWithLogging.post()
                            .uri("trxD/getByPemesan")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(dGetRequest)
                            .retrieve()
                            .bodyToMono(TRXDetailDao.class)
                            .block();

            log.info("trx :" + trxDetailDao1);
            if (trxDetailDao1 == null) {
                throw new RuntimeException("pesanan tidak ada");
            }
            log.info(" start : trxD/us/{}", trxDetailDao1.getId());
            TRXDetailDao tds = webClientWithLogging.put()
                    .uri(uriBuilder -> uriBuilder.path("trxD/us/{id}").build(trxDetailDao1.getId()))
                    .retrieve()
                    .bodyToMono(TRXDetailDao.class)
                    .block();
            log.info(" tds {}", tds);

            log.info(" start : update-status/{}", trxDetailDao1.getId());
            TRXDetailDao txd1 = webClientWithLogging.put()
                    .uri(uriBuilder -> uriBuilder.path("update-status/{id}").build(trxDetailDao1.getId()))
                    .retrieve()
                    .bodyToMono(TRXDetailDao.class)
                    .block();

            log.info(" end trd1 {}",txd1);
            if (txd1 == null) {
                throw new RuntimeException("update gagal");
            }

            PembayaranManual pembayaranManual = new PembayaranManual();
            Long kembali = request.getDiterima() - trxDetailDao1.getTotalBayar();
            if (request.getDiterima() > 0) {
                pembayaranManual.setStatusPembayaran(StatusPembayaran.DIBAYAR);
                pembayaranManual.setDiterima(request.getDiterima());
                pembayaranManual.setKembalian(kembali);
                pembayaranManual.setPenerima(trxDetailDao1.getPemesan());
                pembayaranManual.setCreatedAt(LocalDate.now());
                pembayaranManual.setCreatedTime(LocalTime.now());

                repository.save(pembayaranManual);

            }
            return ResponseEntity.ok("pembayaran berhasil");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            clientResponse.bodyToMono(String.class)
                    .doOnNext(body -> log.info("Response: {}", body))
                    .subscribe();
            return Mono.just(clientResponse);
        });
    }
}
