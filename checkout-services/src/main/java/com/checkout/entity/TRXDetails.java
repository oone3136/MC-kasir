package com.checkout.entity;

import com.checkout.status.StatusTRXD;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "checkout")
@EqualsAndHashCode(exclude = "checkout")
public class TRXDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "pemesan", nullable = false)
    private String pemesan;

    @Column(name = "total_bayar", nullable = false)
    private Long totalBayar;

    @Enumerated(EnumType.STRING)
    private StatusTRXD statusTRXD;

    @ManyToMany
    @JoinTable(name = "trxDcheckout",
    joinColumns = @JoinColumn(name = "trxDetails_id"),
    inverseJoinColumns = @JoinColumn(name = "checkout_id"))
    private Set<Checkout> checkout;
}
