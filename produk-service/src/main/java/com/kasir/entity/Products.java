package com.kasir.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private Long price;
    @Column(name = "harga_jual", nullable = false)
    private Long hargaJual;
    @Column(name = "stock", nullable = false)
    private Integer stock;

    private String categoriName;

    @Lob
    @Column(name = "image_products")
    private byte[] imageProducts;
}
