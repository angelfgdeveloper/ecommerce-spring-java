package com.angelfg.ecommerce.service.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String title;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean available;
}
