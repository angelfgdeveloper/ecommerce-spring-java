package com.angelfg.ecommerce.persistence.mapper;

import com.angelfg.ecommerce.persistence.entity.UserAccessEntity;
import com.angelfg.ecommerce.service.dto.UserAccessDTO;
import com.angelfg.ecommerce.service.dto.UserAccessResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccessMapper {
    UserAccessEntity toEntity(UserAccessDTO userAccessDTO);
    UserAccessDTO toDTO(UserAccessEntity userAccessEntity);
    UserAccessResponseDTO toResponseDTO(UserAccessEntity userAccessEntity);
}
