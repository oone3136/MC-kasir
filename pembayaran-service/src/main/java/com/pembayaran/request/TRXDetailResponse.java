package com.pembayaran.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class  TRXDetailResponse{

    private String id;
    private Products products;
    private Long subtotal;

    @Data
    private static class Products {
        private int id;
        private String name;
        private Long price;
    }
}
