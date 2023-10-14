package com.angelfg.ecommerce.persistence.repository;

import com.angelfg.ecommerce.persistence.entity.RoleEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface RoleRepository extends ListCrudRepository<RoleEntity, Long> {
    boolean existsByName(String name);
}
