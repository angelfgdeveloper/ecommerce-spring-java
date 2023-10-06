package com.angelfg.ecommerce.web.controller;

import com.angelfg.ecommerce.service.dto.ListProductDTO;
import com.angelfg.ecommerce.service.dto.ProductDTO;
import com.angelfg.ecommerce.service.dto.ProductResponseDTO;
import com.angelfg.ecommerce.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ListProductDTO> getProductsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int elements
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsPage(page, elements));
    }

    @GetMapping("/list-order")
    public ResponseEntity<ListProductDTO> getProductsPageSort(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int elements,
            @RequestParam(defaultValue = "idProduct") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        String path = "/api/products/list-order";
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsPageSort(page, elements, sortBy, sortDirection, path));
    }

}
