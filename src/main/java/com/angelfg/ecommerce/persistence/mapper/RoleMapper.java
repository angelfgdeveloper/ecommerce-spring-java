package com.angelfg.ecommerce.persistence.mapper;

import com.angelfg.ecommerce.persistence.entity.RoleEntity;
import com.angelfg.ecommerce.service.dto.RoleDTO;
import com.angelfg.ecommerce.service.dto.RoleResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleEntity toEntity(RoleDTO roleDTO);

    RoleDTO toDTO(RoleEntity roleEntity);

    RoleResponseDTO toResponseDTO(RoleEntity roleEntity);
}
