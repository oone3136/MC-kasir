package com.accounting.pembukuan.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LBRequest {
    private String deskripsi;
    private Long debit;
    private Long kredit;
    private Long pending;
    private String keterangan;
}
