package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
//    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence", allocationSize = 1)
//    @Column(name = "id_product", nullable = false)
//    private Long idProduct;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_product", nullable = false)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_sequence")
    @SequenceGenerator(name = "products_sequence", sequenceName = "products_sequence", allocationSize = 1)
    @Column(name = "id_product", nullable = false)
    private Long idProduct;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(nullable = false)
    private Boolean available = false;

//    @ManyToOne
//    @JoinColumn(name = "id_user", insertable = false, updatable = false)
//    private UserEntity user;

}
