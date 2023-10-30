package com.angelfg.ecommerce.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ProductResponseDTO implements Serializable {
    private Long idProduct;
    private String title;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean available;
}
