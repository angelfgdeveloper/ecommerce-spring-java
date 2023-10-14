package com.angelfg.ecommerce.service;

import com.angelfg.ecommerce.persistence.entity.RoleEntity;
import com.angelfg.ecommerce.persistence.mapper.RoleMapper;
import com.angelfg.ecommerce.persistence.repository.RoleRepository;
import com.angelfg.ecommerce.service.dto.RoleDTO;
import com.angelfg.ecommerce.service.dto.RoleResponseDTO;
import com.angelfg.ecommerce.service.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleRepository roleRepository;

    public RoleService() {}

    public RoleResponseDTO save(RoleDTO roleDTO, String path) {

        if (existNameRole(roleDTO.getName(), path)) {
            throw new CustomException("Ya existe un rol con ese nombre", HttpStatus.BAD_REQUEST, path);
        }

        roleDTO.setName(roleDTO.getName().trim().toUpperCase());
        RoleEntity roleEntity = roleMapper.toEntity(roleDTO);

        RoleEntity newRoleEntity = roleRepository.save(roleEntity);

        return roleMapper.toResponseDTO(newRoleEntity);
    }

    public List<RoleResponseDTO> getAll(String path) {
        try {
            return roleRepository.findAll().stream().map(roleMapper::toResponseDTO).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Hubo un error al intentar obtener todos los roles", HttpStatus.BAD_REQUEST, path);
        }
    }

    public boolean existNameRole(String name, String path) {
        if (name == null || name.isEmpty())
            throw new CustomException("El nombre del rol es inválido", HttpStatus.BAD_REQUEST, path);

        return roleRepository.existsByName(name);
    }

}
