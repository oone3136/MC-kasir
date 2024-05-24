package com.pembayaran.request;

import lombok.Data;
import com.pembayaran.status.StatusPembayaran;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PMCreateRequest {



    private Long diterima;
    private Long kembalian;
    private String penerima;

    private StatusPembayaran statusPembayaran;

    private LocalDate createdAt;
    private LocalTime createdTime;
}
