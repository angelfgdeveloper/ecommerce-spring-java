package com.angelfg.ecommerce.service.user;

import com.angelfg.ecommerce.persistence.entity.UserEntity;
import com.angelfg.ecommerce.persistence.mapper.UserMapper;
import com.angelfg.ecommerce.persistence.repository.UserRepository;
import com.angelfg.ecommerce.service.dto.UserDTO;
import com.angelfg.ecommerce.service.dto.UserResponseDTO;
import com.angelfg.ecommerce.service.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.angelfg.ecommerce.util.GenerateHash.passwordEncoder;

@Controller
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserService() {}

    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream().map(userMapper::toResponseDTO).toList();
    }

    public UserResponseDTO save(UserDTO userDTO, String path) {

        if (existUserByEmail(userDTO.getEmail(), path)) {
            throw new CustomException("Ya existe un usuario con esas credenciales", HttpStatus.BAD_REQUEST, path);
        }

        String hashPassword = passwordEncoder().encode(userDTO.getPassword());
        userDTO.setPassword(hashPassword);

        UserEntity userEntity = userMapper.toEntity(userDTO);

        UserEntity newUserEntity = userRepository.save(userEntity);

        return userMapper.toResponseDTO(newUserEntity);
    }

    public boolean existUserByEmail(String email, String path) {
        if (email == null || email.isEmpty())
            throw new CustomException("El correo ingresado es inválido", HttpStatus.BAD_REQUEST, path);

        return userRepository.existsByEmail(email);
    }

    public UserEntity findUserById(Long id, String path) {
        if (id == null)
            throw new CustomException("El id del usuario ingresado es inválido", HttpStatus.BAD_REQUEST, path);

        return userRepository.findById(id).orElse(null);
    }

    public UserResponseDTO transformUserEntityToDTO(UserEntity user) {
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO findByEmail(String email) {
        return userMapper.toResponseDTO(userRepository.findByEmail(email));
    }
}
