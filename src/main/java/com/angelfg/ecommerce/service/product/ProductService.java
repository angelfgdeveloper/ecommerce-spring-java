package com.angelfg.ecommerce.service.product;

import com.angelfg.ecommerce.persistence.entity.ProductEntity;
import com.angelfg.ecommerce.persistence.mapper.ProductMapper;
import com.angelfg.ecommerce.persistence.repository.ProductPageableRepository;
import com.angelfg.ecommerce.persistence.repository.ProductRepository;
import com.angelfg.ecommerce.service.dto.ListProductDTO;
import com.angelfg.ecommerce.service.dto.ProductDTO;
import com.angelfg.ecommerce.service.dto.ProductResponseDTO;
import com.angelfg.ecommerce.service.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPageableRepository productPageableRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ProductService() {}

    // @CachePut(value = "getAll", key = "'allProducts'", cacheManager = "cacheManager") // Para decirle al cache que actualice ese dato
    @CacheEvict(value = "getAll", key = "'allProducts'", cacheManager = "cacheManager") // Para decirle al cache que actualice algo diferente a su value
    @Secured("ROLE_USER") // Protecciones desde el service, se tiene que configurar el SecurityConfig ocn @EnableMethodSecurity
    @Transactional
    public ProductResponseDTO save(ProductDTO productDTO, String path) {
        try {

            ProductEntity product = productMapper.toEntity(productDTO);

//            // Obtén el próximo valor de la secuencia antes de reiniciarla
//            Query newQuery = entityManager.createNativeQuery("SELECT nextval('products_sequence')");
//            Long newLastSequence = (Long) newQuery.getSingleResult();
//
//            // Reinicia la secuencia para que sea mayor o igual que el nuevo valor deseado
//            Query alterSequenceQuery = entityManager.createNativeQuery("ALTER SEQUENCE products_sequence RESTART WITH " + (newLastSequence + 1));
//            alterSequenceQuery.executeUpdate(); // Ejecuta la consulta ALTER SEQUENCE
//
//            // Asigna el nuevo valor de la secuencia como ID
//            product.setIdProduct(newLastSequence);

            ProductEntity newProduct = productRepository.save(product);
            return productMapper.toEntityResponse(newProduct);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST, path);
        }
    }

    @Cacheable(value = "getAll", key = "'allProducts'", cacheManager = "cacheManager") // Para guardar cache
    public List<ProductResponseDTO> getAll() {
//        return productRepository.findAll().stream().map(productMapper::toEntityResponse).toList();
        List<ProductEntity> products = productRepository.findAll();
        return products.stream().map(productMapper::toEntityResponse).toList();
    }

    public ListProductDTO getProductsPage(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        Page<ProductEntity> productPage = productPageableRepository.findAll(pageRequest);

        Page<ProductResponseDTO> productResponsePage = productPage.map(productEntity -> {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setIdProduct(productEntity.getIdProduct());
            productResponseDTO.setTitle(productEntity.getTitle());
            productResponseDTO.setDescription(productEntity.getDescription());
            productResponseDTO.setPrice(productEntity.getPrice());
            productResponseDTO.setStock(productEntity.getStock());
            productResponseDTO.setAvailable(productEntity.getAvailable());

            return productResponseDTO;
        });

        List<ProductResponseDTO> productsResponse = productResponsePage.getContent();
        int totalPages = productResponsePage.getTotalPages();
        long totalElements = productResponsePage.getTotalElements();
        int numberOfElements = productResponsePage.getNumberOfElements();
        int pageNumber = productResponsePage.getPageable().getPageNumber();
        int pageSize = productResponsePage.getPageable().getPageSize();

        return new ListProductDTO(
            totalPages,
            totalElements,
            numberOfElements,
            pageNumber,
            pageSize,
            productsResponse
        );
    }

    public ListProductDTO getProductsPageSort(
        int page, int elements, String sortBy, String sortDirection, String path
    ) {

        if (page < 0) {
            throw new CustomException(
                "El parametro page no puede contener valores negativos",
                HttpStatus.BAD_REQUEST,
                path
            );
        }

        if (elements <= 0) {
            throw new CustomException(
                "El parametro elements no puede contener valores igual o menor a cero",
                HttpStatus.BAD_REQUEST,
                path
            );
        }

        List<String> sortByKeys = Arrays.asList("idProduct", "title", "description", "price", "stock", "available");

        boolean existKey = sortByKeys.stream().anyMatch(keys -> Objects.equals(sortBy.trim(), keys));

        if (!existKey) {
            throw new CustomException(
                "El parametro sortBy solo puede ser: " + sortByKeys,
                HttpStatus.BAD_REQUEST,
                path
            );
        }

        if (!Objects.equals(sortDirection.trim().toUpperCase(), "ASC") && !Objects.equals(sortDirection.trim().toUpperCase(), "DESC")) {
            throw new CustomException(
                "El parametro sortDirection solo puede ser: ASC ó DESC",
                HttpStatus.BAD_REQUEST,
                path
            );
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        Page<ProductEntity> productPage = productPageableRepository.findAll(pageRequest);

        Page<ProductResponseDTO> productResponsePage = productPage.map(productMapper::toEntityResponse);

        List<ProductResponseDTO> productsResponse = productResponsePage.getContent();
        int totalPages = productResponsePage.getTotalPages();
        long totalElements = productResponsePage.getTotalElements();
        int numberOfElements = productResponsePage.getNumberOfElements();
        int pageNumber = productResponsePage.getPageable().getPageNumber();
        int pageSize = productResponsePage.getPageable().getPageSize();

        return new ListProductDTO(
            totalPages,
            totalElements,
            numberOfElements,
            pageNumber,
            pageSize,
            productsResponse
        );
    }
}
