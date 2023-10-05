package com.angelfg.ecommerce.web.controller;

import com.angelfg.ecommerce.persistence.entity.ProductEntity;
import com.angelfg.ecommerce.persistence.entity.UserEntity;
import com.angelfg.ecommerce.service.dto.ProductDTO;
import com.angelfg.ecommerce.service.dto.ProductResponseDTO;
import com.angelfg.ecommerce.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    public ProductController() {}

    @PostMapping
    public ResponseEntity<ProductResponseDTO> add(@RequestBody ProductDTO productDTO) {
        String path = "/api/products";
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDTO, path));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll());
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProductResponseDTO>> getAllAvailable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int elements
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllAvailable(page, elements));
    }

}
