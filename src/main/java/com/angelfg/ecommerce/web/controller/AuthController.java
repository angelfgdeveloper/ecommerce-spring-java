package com.angelfg.ecommerce.web.controller;

import com.angelfg.ecommerce.service.AuthService;
import com.angelfg.ecommerce.service.dto.LoginDTO;
import com.angelfg.ecommerce.service.dto.UserWithTokenResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    public AuthController() {}

    @PostMapping("/login")
    public ResponseEntity<UserWithTokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        String path = "/api/auth/login";
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(loginDTO, path));
    }

    @GetMapping("/regenerate-token")
    public ResponseEntity<UserWithTokenResponseDTO> regenerateToken(
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        String path = "/api/auth/regenerate-token";
        return ResponseEntity.status(HttpStatus.OK).body(authService.reNewOldToken(authorizationHeader, path));
    }

}
