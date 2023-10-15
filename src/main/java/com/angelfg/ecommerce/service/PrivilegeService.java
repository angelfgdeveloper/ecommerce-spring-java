package com.angelfg.ecommerce.service;

import com.angelfg.ecommerce.persistence.entity.PrivilegeEntity;
import com.angelfg.ecommerce.persistence.mapper.PrivilegeMapper;
import com.angelfg.ecommerce.persistence.repository.PrivilegeRepository;
import com.angelfg.ecommerce.service.dto.PrivilegeDTO;
import com.angelfg.ecommerce.service.dto.PrivilegeResponseDTO;
import com.angelfg.ecommerce.service.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    private final Logger log = LoggerFactory.getLogger(PrivilegeService.class);

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    private PrivilegeService() {}

    public PrivilegeResponseDTO save(PrivilegeDTO privilegeDTO, String path) {

        if (existNamePrivilege(privilegeDTO.getName(), path)) {
            throw new CustomException("Ya existe un privilegio con ese nombre", HttpStatus.BAD_REQUEST, path);
        }

        privilegeDTO.setName(privilegeDTO.getName().trim().toUpperCase());
        PrivilegeEntity privilegeEntity = privilegeMapper.toEntity(privilegeDTO);

        PrivilegeEntity newPrivilegeEntity = privilegeRepository.save(privilegeEntity);

        return privilegeMapper.toResponseDTO(newPrivilegeEntity);
    }

    public boolean existNamePrivilege(String name, String path) {
        if (name == null || name.isEmpty())
            throw new CustomException("El nombre del privilegio es inválido", HttpStatus.BAD_REQUEST, path);

        return privilegeRepository.existsByName(name);
    }

    public List<PrivilegeResponseDTO> getAll(String path) {
        try {
            return privilegeRepository.findAll().stream().map(privilegeMapper::toResponseDTO).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Hubo un error al intentar obtener todos los privilegios", HttpStatus.BAD_REQUEST, path);
        }
    }

    public PrivilegeEntity findPrivilegeById(Long id, String path) {
        if (id == null)
            throw new CustomException("El id del privilegio ingresado es inválido", HttpStatus.BAD_REQUEST, path);

        return privilegeRepository.findById(id).orElse(null);
    }

    public PrivilegeResponseDTO transformPrivilegeEntityToDTO(PrivilegeEntity privilege) {
        return privilegeMapper.toResponseDTO(privilege);
    }
}
