package com.pembayaran.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TRXDetailDao {
    private  Integer id;
    private String pemesan;
    private Long totalBayar;

//    private String id;
//    private int quantity;
//    private Long subtotal;
//    private TransactionId transactionId;
//
//    @Data
//    public static class TransactionId {
//        private String id;
//        @JsonProperty("products")
//        private Product product;
//    }
//    @Data
//    public static class Product {
//        private Long id;
//        private String name;
//        private Long hargaJual;
//        private int stock;
//        private Category categoriName;
//
//        @Data
//        public static class Category {
//            private int id;
//            private String nama;
//        }
//    }
}
