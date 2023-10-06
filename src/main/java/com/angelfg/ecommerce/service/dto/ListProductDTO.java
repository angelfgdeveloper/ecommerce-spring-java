package com.angelfg.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class ListProductDTO {
    int totalPages;
    long totalElements;
    int numberOfElements;
    int pageNumber;
    int pageSize;
    List<ProductResponseDTO> productsResponse;
}
