package com.angelfg.ecommerce.service;

import com.angelfg.ecommerce.service.component.JwtUtil;
import com.angelfg.ecommerce.service.dto.LoginDTO;
import com.angelfg.ecommerce.service.dto.UserResponseDTO;
import com.angelfg.ecommerce.service.dto.UserWithTokenResponseDTO;
import com.angelfg.ecommerce.service.exception.CustomException;
import com.angelfg.ecommerce.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthService() {}

    public UserWithTokenResponseDTO login(LoginDTO loginDTO, String path) {

        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
            loginDTO.getEmail(),
            loginDTO.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(login);

        String token = jwtUtil.create(loginDTO.getEmail());
        String email = jwtUtil.getEmail(token);

        if (email == null || email.isEmpty()) {
            throw new CustomException("Hubo un error al intentar realizar el login", HttpStatus.BAD_REQUEST, path);
        }

        UserResponseDTO userResponseDTO = userService.findByEmail(email);

        return new UserWithTokenResponseDTO(userResponseDTO, token);
    }

    public UserWithTokenResponseDTO reNewOldToken(String authorizationHeader, String path) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String oldToken = authorizationHeader.split(" ")[1].trim();

            if (!jwtUtil.isValidToken(oldToken)) {
                throw new CustomException("El token no es válido", HttpStatus.UNAUTHORIZED, path);
            }

            String email = jwtUtil.getEmail(oldToken);

            if (email == null || email.isEmpty()) {
                throw new CustomException("Hubo un problema al actualizar el token", HttpStatus.BAD_REQUEST, path);
            }

            String newToken = jwtUtil.create(email);

            UserResponseDTO userResponseDTO = userService.findByEmail(email);

            return new UserWithTokenResponseDTO(userResponseDTO, newToken);
        } else {
            throw new CustomException("El token no es válido", HttpStatus.UNAUTHORIZED, path);
        }

    }

}
