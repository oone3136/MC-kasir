package com.checkout.request;

import lombok.Data;

@Data
public class CheckRequest {

    private String products;
    private Integer quantity;
    private String pemesan;

}
