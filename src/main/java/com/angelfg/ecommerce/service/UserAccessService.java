package com.angelfg.ecommerce.service;

import com.angelfg.ecommerce.persistence.entity.PrivilegeEntity;
import com.angelfg.ecommerce.persistence.entity.RoleEntity;
import com.angelfg.ecommerce.persistence.entity.UserAccessEntity;
import com.angelfg.ecommerce.persistence.entity.UserEntity;
import com.angelfg.ecommerce.persistence.mapper.UserAccessMapper;
import com.angelfg.ecommerce.persistence.repository.UserAccessRepository;
import com.angelfg.ecommerce.service.dto.*;
import com.angelfg.ecommerce.service.exception.CustomException;
import com.angelfg.ecommerce.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAccessService {

    private final Logger log = LoggerFactory.getLogger(UserAccessService.class);

    @Autowired
    private UserAccessMapper userAccessMapper;

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;

    public UserAccessService() {}

    @Transactional
    public UserAccessResponseDTO save(UserAccessDTO userAccessDTO, String path) {

        Long idUser = userAccessDTO.getIdUser();
        Long idRole = userAccessDTO.getIdRole();
        Long idPrivilege = userAccessDTO.getIdPrivilege();

        if (existUserAccess(idUser, idRole, idPrivilege)) throw new CustomException("El acceso al usuario ya existe", HttpStatus.BAD_REQUEST, path);

        UserEntity userEntity = userService.findUserById(idUser, path);
        RoleEntity roleEntity = roleService.findRoleById(idRole, path);
        PrivilegeEntity privilegeEntity = privilegeService.findPrivilegeById(idPrivilege, path);

        if (userEntity == null) throw new CustomException("El id del usuario ingresado no existe", HttpStatus.BAD_REQUEST, path);
        if (roleEntity == null) throw new CustomException("El id del rol ingresado no existe", HttpStatus.BAD_REQUEST, path);
        if (privilegeEntity == null) throw new CustomException("El id del privilegio ingresado no existe", HttpStatus.BAD_REQUEST, path);

        UserAccessEntity userAccessEntity = new UserAccessEntity(
            roleEntity,
            privilegeEntity,
            userEntity
        );

        UserAccessEntity newUserAccessEntity = userAccessRepository.save(userAccessEntity);

        RoleResponseDTO roleResponseDTO = roleService.transformRoleEntityToDTO(newUserAccessEntity.getRole());
        PrivilegeResponseDTO privilegeResponseDTO = privilegeService.transformPrivilegeEntityToDTO(newUserAccessEntity.getPrivilege());
        UserResponseDTO userResponseDTO = userService.transformUserEntityToDTO(newUserAccessEntity.getUser());

        return new UserAccessResponseDTO(
            newUserAccessEntity.getIdUserAccess(),
            roleResponseDTO,
            privilegeResponseDTO,
            userResponseDTO,
            newUserAccessEntity.getCreated_at()
        );

    }

    public List<UserAccessEntity> getAll(String path) {
        try {
            return userAccessRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Hubo un error al intentar obtener todos los accesos de los usuarios", HttpStatus.BAD_REQUEST, path);
        }
    }

    private boolean existUserAccess(Long idUser, Long idRole, Long idPrivilege) {
        UserAccessEntity userAccessEntity = userAccessRepository.existUserAccesEntityByRoleIdAndPrivilegeIdAndUserId(idUser, idRole, idPrivilege).orElse(null);
        return (userAccessEntity == null) ? false : true;
    }

}