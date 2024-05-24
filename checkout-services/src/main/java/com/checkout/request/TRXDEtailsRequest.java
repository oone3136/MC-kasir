package com.checkout.request;

import com.checkout.entity.Checkout;
import lombok.Data;

import java.util.List;

@Data
public class TRXDEtailsRequest {

    private String pemesan;
    private List<Checkout> checkout;
}
