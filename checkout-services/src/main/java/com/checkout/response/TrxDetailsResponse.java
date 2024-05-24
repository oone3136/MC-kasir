package com.checkout.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrxDetailsResponse {
    private Integer id;
    private String pemesan;
    private Long totalBayar;
}
