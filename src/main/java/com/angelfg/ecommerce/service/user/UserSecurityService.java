package com.angelfg.ecommerce.service.user;

import com.angelfg.ecommerce.persistence.entity.UserEntity;
import com.angelfg.ecommerce.persistence.mapper.UserMapper;
import com.angelfg.ecommerce.persistence.repository.UserRepository;
import com.angelfg.ecommerce.service.UserAccessService;
import com.angelfg.ecommerce.service.dto.UserAccessRolesAndPrivilegesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAccessService userAccessService;

    public UserSecurityService() {}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Correo " + email + " no encontrado");
        }

//        List<String> nameRolesByUser = userAccessService.findAccessByRoleIdOfUserId(userEntity.getIdUser());
//        String[] nameRolesByUser = userAccessService.findAccessByRoleIdOfUserId(userEntity.getIdUser()).toArray(String[]::new);
        UserAccessRolesAndPrivilegesDTO userAccessRolesAndPrivilegesDTO = userAccessService.findUserAccessByRoleAndByPrivilegeOfUserId(userEntity.getIdUser());

        String[] nameRolesByUser = userAccessRolesAndPrivilegesDTO.getRoles();
        String[] namePrivilegesByUser = userAccessRolesAndPrivilegesDTO.getPrivileges();

//        if (nameRolesByUser == null || nameRolesByUser.length == 0) {
//            throw new UsernameNotFoundException("Hubo un error al intentar encontrar los roles del usuario");
//        }
//
//        if (namePrivilegesByUser == null || namePrivilegesByUser.length == 0) {
//            throw new UsernameNotFoundException("Hubo un error al intentar encontrar los privilegios del usuario");
//        }

//        String[] rolesByUser = nameRolesByUser.toArray(new String[0]);

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
//                .roles(nameRolesByUser)
                .authorities(this.grantedAuthorities(nameRolesByUser, namePrivilegesByUser))
                .accountLocked(userEntity.getLocked())
                .disabled(userEntity.getDisabled())
                .build();
    }

    private String[] getAuthorities(String role) {
        if ("ADMIN".equals(role)) {
            return new String[] { "ACCESS_VIEW_ROLES" };
        }

        return new String[] {};
    }

    private List<GrantedAuthority> grantedAuthorities(String[] roles, String[] privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            for (String authority : privileges) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }

        }

        return authorities;
    }
}
