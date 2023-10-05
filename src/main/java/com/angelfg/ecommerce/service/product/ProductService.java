package com.angelfg.ecommerce.service.product;

import com.angelfg.ecommerce.persistence.entity.ProductEntity;
import com.angelfg.ecommerce.persistence.mapper.ProductMapper;
import com.angelfg.ecommerce.persistence.repository.ProductPageableRepository;
import com.angelfg.ecommerce.persistence.repository.ProductRepository;
import com.angelfg.ecommerce.service.dto.ProductDTO;
import com.angelfg.ecommerce.service.dto.ProductResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPageableRepository productPageableRepository;

    public ProductService() {}

    public ProductResponseDTO save(ProductDTO productDTO, String path) {
        ProductEntity product = productMapper.toEntity(productDTO);
        ProductEntity newProduct = productRepository.save(product);
        return productMapper.toEntityResponse(newProduct);
    }

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll().stream().map(productMapper::toEntityResponse).toList();
    }

    public Page<ProductResponseDTO> getAllAvailable(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        Page<ProductEntity> productPage = productPageableRepository.findAll(pageRequest);

        return productPage.map(productEntity -> {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setIdProduct(productEntity.getIdProduct());
            productResponseDTO.setTitle(productEntity.getTitle());
            productResponseDTO.setDescription(productEntity.getDescription());
            productResponseDTO.setPrice(productEntity.getPrice());
            productResponseDTO.setStock(productEntity.getStock());
            productResponseDTO.setAvailable(productEntity.getAvailable());

            return productResponseDTO;
        });
    }
}
