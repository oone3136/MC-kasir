package com.checkout.services;

import com.checkout.entity.Checkout;
import com.checkout.entity.TRXDetails;
import com.checkout.repository.CheckoutRepository;
import com.checkout.repository.TRXDRepository;
import com.checkout.request.UpdateStatusCheckoutRequest;
import com.checkout.status.StatusCheckout;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateStatusCheckout {

    private final CheckoutRepository repository;
    private final TRXDRepository trxdRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateStatusCheckout.class);

    @Transactional
    public void updateCheckoutStatus(Integer trxDetailsId) {
        TRXDetails trxDetails = trxdRepository.findById(trxDetailsId)
                .orElseThrow(() -> new IllegalArgumentException("TRXDetails not found with ID: " + trxDetailsId));

        logger.info("id : {}", trxDetailsId);
        logger.info("find By id : {}", trxDetails);

        Set<Checkout> checkouts = trxDetails.getCheckout();
        for (Checkout checkout : checkouts) {
            checkout.setStatus(StatusCheckout.DICHECKOUT);
            repository.save(checkout);
            logger.info("checkouts : {}", checkouts);
            logger.info("checkout : {}", checkout);

        }
    }

    @Transactional
    public void updateCheckoutStatusWithBody(Integer trxDetailsId, List<UpdateStatusCheckoutRequest> updates) {
        TRXDetails trxDetails = trxdRepository.findByIdWithCheckout(trxDetailsId);
        if (trxDetails == null) {
            throw new RuntimeException("TRXDetails not found with ID: " + trxDetailsId);
        }

        logger.info("updates : {}", updates);
        logger.info("trxDetailsId : {}", trxDetailsId );
        logger.info("trxDetails : {}", trxDetails);

        Set<Checkout> checkouts = trxDetails.getCheckout();
        logger.info("checkout : {}", checkouts);

        for (UpdateStatusCheckoutRequest update : updates) {
            for (Checkout checkout : checkouts) {
                logger.info("for Checkout {} to {}" , checkout, checkouts);
                if (checkout.getId().toString().equals(update.getId())) {
                    checkout.setStatus(StatusCheckout.valueOf(update.getStatus()));
                    logger.info("checkout : {}", checkout);
                    repository.save(checkout);
                }
            }
        }
    }
}
