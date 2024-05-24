package com.checkout.repository;

import com.checkout.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, String> {
    Optional<Checkout> findByProducts(String products);
}
