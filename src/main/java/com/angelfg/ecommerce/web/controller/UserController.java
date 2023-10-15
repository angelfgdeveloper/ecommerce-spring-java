package com.angelfg.ecommerce.web.controller;

import com.angelfg.ecommerce.service.dto.UserDTO;
import com.angelfg.ecommerce.service.dto.UserResponseDTO;
import com.angelfg.ecommerce.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.angelfg.ecommerce.service.dto.UserDTO.validUserDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public UserController() {}

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> add(@RequestBody UserDTO userDTO) {
        String path = "/api/users";
        validUserDTO(userDTO, path);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO, path));
    }

}
