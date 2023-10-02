package com.angelfg.ecommerce.persistence.repository;

import com.angelfg.ecommerce.persistence.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends ListCrudRepository<UserEntity, Long> {

    @Query(value = "SELECT u FROM UserEntity u WHERE u.email = :email", nativeQuery = false)
    UserEntity findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

}
