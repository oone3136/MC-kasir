package com.checkout.request;

import com.checkout.status.StatusCheckout;
import lombok.Data;

@Data
public class UpdateStatusCheckoutRequest {

    private String id;
    private String status;
}
