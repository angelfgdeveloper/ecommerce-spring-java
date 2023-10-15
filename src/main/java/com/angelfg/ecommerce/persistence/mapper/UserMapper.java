package com.angelfg.ecommerce.persistence.mapper;

import com.angelfg.ecommerce.persistence.entity.UserEntity;
import com.angelfg.ecommerce.service.dto.UserDTO;
import com.angelfg.ecommerce.service.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends TransformMapperBase<UserDTO, UserEntity> {
    UserResponseDTO toResponseDTO(UserEntity userEntity);
}
