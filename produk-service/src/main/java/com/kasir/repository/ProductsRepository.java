package com.kasir.repository;

import com.kasir.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

    @Modifying
    @Transactional
    @Query("update Products u set u.imageProducts = ?1 where u.id =?2")
    int updateImageProducts(byte[] image, Integer id);
}
