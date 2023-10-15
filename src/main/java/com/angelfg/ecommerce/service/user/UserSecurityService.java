package com.angelfg.ecommerce.service.user;

import com.angelfg.ecommerce.persistence.entity.UserEntity;
import com.angelfg.ecommerce.persistence.mapper.UserMapper;
import com.angelfg.ecommerce.persistence.repository.UserRepository;
import com.angelfg.ecommerce.service.UserAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        String[] nameRolesByUser = userAccessService.findAccessByRoleIdOfUserId(userEntity.getIdUser()).toArray(String[]::new);

        if (nameRolesByUser == null || nameRolesByUser.length == 0) {
            throw new UsernameNotFoundException("Hubo un error al intentar encontrar los roles del usuario");
        }

//        String[] rolesByUser = nameRolesByUser.toArray(new String[0]);

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(nameRolesByUser)
                .accountLocked(userEntity.getLocked())
                .disabled(userEntity.getDisabled())
                .build();
    }
}
