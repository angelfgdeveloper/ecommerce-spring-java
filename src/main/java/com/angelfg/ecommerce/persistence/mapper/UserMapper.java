package com.angelfg.ecommerce.persistence.mapper;

import com.angelfg.ecommerce.persistence.entity.UserEntity;
import com.angelfg.ecommerce.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final Logger log = LoggerFactory.getLogger(UserMapper.class);

    public UserMapper () {}

    public UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(userEntity.getName());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        
        return userDTO;
    }

    public UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setDisabled(false);
        userEntity.setLocked(false);

        return userEntity;
    }

}
