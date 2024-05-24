package com.pembayaran.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.pembayaran.status.StatusPembayaran;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "pembayaran_manual")
public class PembayaranManual {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @Column(name = "product_id", nullable = false)
//    private Long product;
    @Column(name = "uang_diterima")
    private Long diterima;
    @Column(name = "kembalian")
    private Long kembalian;
    @Column(name = "penerima", nullable = false)
    private String penerima;

    private StatusPembayaran statusPembayaran;

    private LocalDate createdAt;
    private LocalTime createdTime;

    private LocalDate updatedAt;
    private LocalDate updateTime;

}
