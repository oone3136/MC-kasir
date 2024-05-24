package com.kasir.services;

import com.kasir.entity.Products;
import com.kasir.request.SendData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SendDataImpl {
    private final WebClient webClientLaporan;

    public void sendReport(@RequestBody Products products) {
        SendData sendData = new SendData();
        sendData.setDeskripsi("Pembelian " + products.getName());
        sendData.setKredit(products.getPrice());
        webClientLaporan.post()
                .uri("/lh1", new Object[0])
                .bodyValue(sendData)
                .retrieve()
                .bodyToMono(Void.TYPE)
                .block();
    }

}
