package com.angelfg.ecommerce.service;

import com.angelfg.ecommerce.persistence.repository.UserRepository;
import com.angelfg.ecommerce.service.component.JwtUtil;
import com.angelfg.ecommerce.service.dto.LoginDTO;
import com.angelfg.ecommerce.service.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthService() {}

    public Object login(LoginDTO loginDTO, String path) {

        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
            loginDTO.getEmail(),
            loginDTO.getPassword()
        );

        log.debug("LOGIN ANTES => " + login.toString());

        Authentication authentication = authenticationManager.authenticate(login);
        log.debug("LOGIN DESPUES => " + authentication.toString());

        log.debug("AUTENTICATION => " + authentication.isAuthenticated());
        log.debug("AUTENTICATION EMAIL => " + authentication.getPrincipal());

        String jwt = jwtUtil.create(loginDTO.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);

        return response;
    }
}