package com.angelfg.ecommerce.persistence.mapper;

import com.angelfg.ecommerce.persistence.entity.PrivilegeEntity;
import com.angelfg.ecommerce.service.dto.PrivilegeDTO;
import com.angelfg.ecommerce.service.dto.PrivilegeResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

    PrivilegeEntity toEntity(PrivilegeDTO privilegeDTO);

    PrivilegeDTO toDto(PrivilegeEntity privilegeEntity);

    PrivilegeResponseDTO toResponseDTO(PrivilegeEntity newPrivilegeEntity);

}
