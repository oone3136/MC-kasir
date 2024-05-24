package com.checkout.entity;

import com.checkout.status.StatusCheckout;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "trxDetails")
@EqualsAndHashCode(exclude = "trxDetails")
public class Checkout {
    @Id
    private String id;
    @Column(name = "productId", nullable = false)
    private String products;

    @Enumerated(EnumType.STRING)
    private StatusCheckout status;
    private Integer quantity;
    private Long subTotal;

    private LocalDate createdAt;
    private LocalTime createdTime;

    private LocalDate updateAt;

    @ManyToMany(mappedBy = "checkout")
    private Set<TRXDetails> trxDetails;
}
