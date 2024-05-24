package com.checkout.controller;

import com.checkout.request.CheckRequest;
import com.checkout.request.UpdateStatusCheckoutRequest;
import com.checkout.services.CreateCheckoutService;
import com.checkout.services.GetTrxDetailsServices;
import com.checkout.services.TRXDDeleteteService;
import com.checkout.services.UpdateStatusCheckout;
import com.checkout.status.StatusCheckout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CheckoutController {

    private final CreateCheckoutService checkoutService;
    private final GetTrxDetailsServices getServices;
    private final UpdateStatusCheckout updateStatusCheckout;


    @PostMapping("/checkout")
    public ResponseEntity<String> createCheckout(@RequestBody CheckRequest request) {
        return checkoutService.createCheckout(request);
    }

    @PutMapping("/update-status/{trxDetailsId}")
    public ResponseEntity<String> updateCheckoutStatus(
            @PathVariable Integer trxDetailsId) {
        updateStatusCheckout.updateCheckoutStatus(trxDetailsId);
        return ResponseEntity.ok("Status updated successfully");
    }

    @PutMapping("/update-status-body/{trxDetailsId}")
    public ResponseEntity<String> updateCheckoutStatusWithBody(
            @PathVariable Integer trxDetailsId,
            @RequestBody List<UpdateStatusCheckoutRequest> updates) {
        updateStatusCheckout.updateCheckoutStatusWithBody(trxDetailsId, updates);
        return ResponseEntity.ok("Status updated successfully with body");
    }

}
