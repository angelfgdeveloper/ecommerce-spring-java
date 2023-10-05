package com.angelfg.ecommerce.persistence.repository;

import com.angelfg.ecommerce.persistence.entity.ProductEntity;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ProductPageableRepository extends ListPagingAndSortingRepository<ProductEntity, Long> {

}
