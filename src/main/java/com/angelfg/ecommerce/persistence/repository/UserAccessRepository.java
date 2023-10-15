package com.angelfg.ecommerce.persistence.repository;

import com.angelfg.ecommerce.persistence.entity.UserAccessEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAccessRepository extends ListCrudRepository<UserAccessEntity, Long> {

    @Query(
        value = "SELECT ua FROM UserAccessEntity ua WHERE ua.role.idRole = :idRole AND " +
                "ua.privilege.idPrivilege = :idPrivilege AND " +
                "ua.user.idUser = :idUser",
        nativeQuery = false
    )
    Optional<UserAccessEntity> existUserAccesEntityByRoleIdAndPrivilegeIdAndUserId(
        @Param("idUser") Long idUser,
        @Param("idRole") Long idRole,
        @Param("idPrivilege") Long idPrivilege
    );

    @Query(
        value = "SELECT ua FROM UserAccessEntity ua WHERE ua.user.idUser = :idUser AND " +
                "ua.disabled = false AND ua.role IS NOT NULL AND ua.privilege IS NOT NULL",
        nativeQuery = false
    )
    List<UserAccessEntity> existsUserId(@Param("idUser") Long idUser);
}
