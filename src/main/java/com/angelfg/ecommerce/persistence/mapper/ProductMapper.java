package com.angelfg.ecommerce.persistence.mapper;

import com.angelfg.ecommerce.persistence.entity.ProductEntity;
import com.angelfg.ecommerce.service.dto.ProductDTO;
import com.angelfg.ecommerce.service.dto.ProductResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(ProductDTO productDTO);

    ProductDTO toDto(ProductEntity productEntity);

    ProductResponseDTO toEntityResponse(ProductEntity productEntity);
}