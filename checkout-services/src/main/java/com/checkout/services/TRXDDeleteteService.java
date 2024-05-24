package com.checkout.services;

import com.checkout.entity.TRXDetails;
import com.checkout.repository.TRXDRepository;
import com.checkout.status.StatusTRXD;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TRXDDeleteteService {

    private final TRXDRepository repository;
    private final Logger log = LoggerFactory.getLogger(TRXDDeleteteService.class);
    public ResponseEntity<String> updateStatusTRXD(Integer id) {
        log.info("start update status");
        TRXDetails trxDetails = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("data not found"));
        log.info("id : {}", id);
        log.info("trxDetails : {}",trxDetails);  trxDetails.setStatusTRXD(StatusTRXD.SELESAI);
        repository.save(trxDetails);
        log.info("trxDetails : {}", trxDetails);
        log.info("end update status");
        return ResponseEntity.ok("berhasil mengubah status transaksi");
    }
}
