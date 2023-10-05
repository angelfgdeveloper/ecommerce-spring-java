package com.angelfg.ecommerce.persistence.repository;

import com.angelfg.ecommerce.persistence.entity.ProductEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<ProductEntity, Long> {

}