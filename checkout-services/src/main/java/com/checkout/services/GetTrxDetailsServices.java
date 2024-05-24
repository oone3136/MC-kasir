package com.checkout.services;

import com.checkout.entity.TRXDetails;
import com.checkout.repository.TRXDRepository;
import com.checkout.request.TRXDByNameRequest;
import com.checkout.response.TrxDetailsResponse;
import com.checkout.status.StatusTRXD;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTrxDetailsServices {

    private final TRXDRepository repository;
    private final Logger log = LoggerFactory.getLogger(GetTrxDetailsServices.class);

    public TrxDetailsResponse getTrxDetails(TRXDByNameRequest request) {
        log.info("request : "+ request);
        if (request.getPemesan().isEmpty()) {
            throw new RuntimeException("pemesan tidak boleh kosong");
        }
        StatusTRXD status = StatusTRXD.DISIAPKAN;
        TRXDetails trxDetails = repository.findByPemesanAndStatusTRXD(request.getPemesan(), status);
        if (trxDetails.getPemesan().isEmpty()) {
            throw new RuntimeException("sorry please cross check pemesan");
        }
        return new TrxDetailsResponse(trxDetails.getId(), trxDetails.getPemesan(), trxDetails.getTotalBayar());



//        log.info("request : "+ request);
//        if (request.getCreated().isEmpty()) {
//            throw new RuntimeException("pembeli tidak boleh kosong");
//        }
//
//        List<Checkout> checkoutList = repository.findByCreated(request.getCreated());
//        log.info("checkout List : " + checkoutList);
//        if (checkoutList.isEmpty()) {
//            throw new RuntimeException("tidak ada data");
//        }
//
//        Checkout checkout = checkoutList.get(0);
//        if (checkout.getSubTotal() == 0) {
//            throw new RuntimeException("total harga tidak ada");
//        }
//
//        Long subTotal = checkoutList.stream().mapToLong(Checkout::getSubTotal).sum();

//        return null;
//                new CheckoutResponse(checkout.isStatus(), subTotal);
    }
}