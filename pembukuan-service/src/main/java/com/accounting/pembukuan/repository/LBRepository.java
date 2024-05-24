package com.accounting.pembukuan.repository;

import com.accounting.pembukuan.entity.LB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LBRepository extends JpaRepository<LB, String> {

    @Query("select l from LB l where l.statusPembayaran = debit and l.tglTrx between :startDate and :endDate")
    List<LB>findByStatusDebit(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("select l from LB l where l.statusPembayaran = kredit and l.tglTrx between :startDate and :endDate")
    List<LB>findByStatusKredit(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);
}
