package com.pembayaran.repository;

import com.pembayaran.entity.PembayaranManual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PMRepository extends JpaRepository<PembayaranManual, Long> {
}
