package com.checkout.request;

import lombok.Data;

@Data
public class ProductRequest {
    private int id;
    private String name;
    private Long hargaJual;
    private Integer stock;
}
