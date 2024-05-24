package com.accounting.pembukuan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "laporan_harian")
public class LB {

    @Id
    private String id;

    @Column(name = "tanggal_transaksi", nullable = false)
    @JsonFormat(pattern = "yy-MM-dd")
    private LocalDate tglTrx;

    @Column(name = "Deskripsi")
    private String deskripsi;

    @Column(name = "status_pembayaran")
    private Status statusPembayaran;

    @Column(name = "debit")
    private Long debit;

    @Column(name = "kredit")
    private Long kredit;

    @Column(name = "pending")
    private Long pending;

    @Column(name = "keterangan")
    private String keterangan;
}
