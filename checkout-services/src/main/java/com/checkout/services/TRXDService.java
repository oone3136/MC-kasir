package com.checkout.services;

import com.checkout.entity.Checkout;
import com.checkout.entity.TRXDetails;
import com.checkout.repository.CheckoutRepository;
import com.checkout.repository.TRXDRepository;
import com.checkout.request.TRXDEtailsRequest;
import com.checkout.status.StatusCheckout;
import com.checkout.status.StatusTRXD;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TRXDService {

    private final TRXDRepository repository;
    private final CheckoutRepository checkoutRepository;
    private final Logger logger = LoggerFactory.getLogger(TRXDService.class);

    public ResponseEntity<String>createTRXD(TRXDEtailsRequest request) {
        logger.info("start : {}, {}", request.getPemesan(), request.getCheckout());
        cekInput(request);
        addData(request);
        logger.info("end trx Details");
        return ResponseEntity.ok("Transaksi Details Hasbeen add");
    }
    private void cekInput(TRXDEtailsRequest request) {
        if (request == null || request.getPemesan() == null || request.getCheckout() == null) {
            throw new RuntimeException("Request tidak valid");
        }
    }
    private void addData(TRXDEtailsRequest request) {

        List<String> checkoutId = request.getCheckout().stream()
                .map(Checkout::getId)
                .toList();
        logger.info("get all check out by Id : {}", checkoutId);

        List<Checkout> checkoutCheck = checkoutRepository.findAllById(checkoutId);
        logger.info("checkout chek : {}" , checkoutCheck);
        if (checkoutCheck.isEmpty() || checkoutCheck.size() != checkoutId.size()) {
            throw new RuntimeException("Satu atau lebih checkout tidak ditemukan");
        }

        boolean hasDICHECKOUT = checkoutCheck.stream()
                .anyMatch(checkout -> checkout.getStatus() == StatusCheckout.DICHECKOUT);
        if (hasDICHECKOUT) {
            throw new RuntimeException("Pesanan ini sudah di checkout");
        }

        Long subTotal = checkoutCheck.stream()
                .mapToLong(Checkout::getSubTotal)
                .sum();
        logger.info("sub total {}", subTotal);


        TRXDetails trxDetails = new TRXDetails();
        trxDetails.setPemesan(request.getPemesan());
        trxDetails.setTotalBayar(subTotal);
        trxDetails.setStatusTRXD(StatusTRXD.DISIAPKAN);
        trxDetails.setCheckout(new HashSet<>(checkoutCheck));

        TRXDetails trxDetails1 = repository.save(trxDetails);
        logger.info("trxd 1 : {}", trxDetails1);
    }
}
