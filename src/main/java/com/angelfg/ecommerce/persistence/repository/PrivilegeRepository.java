package com.angelfg.ecommerce.persistence.repository;

import com.angelfg.ecommerce.persistence.entity.PrivilegeEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface PrivilegeRepository extends ListCrudRepository<PrivilegeEntity, Long>  {
    boolean existsByName(String name);
}
