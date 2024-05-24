package com.checkout.repository;

import com.checkout.entity.TRXDetails;
import com.checkout.status.StatusTRXD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TRXDRepository extends JpaRepository<TRXDetails, Integer> {
    TRXDetails findByPemesan(String pemesan);

    @Query("SELECT t FROM TRXDetails t JOIN FETCH t.checkout WHERE t.id = :id")
    TRXDetails findByIdWithCheckout(@Param("id") Integer id);

    TRXDetails findByPemesanAndStatusTRXD(String pemesan, StatusTRXD status);
}
