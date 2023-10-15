package com.angelfg.ecommerce.web.controller;

import com.angelfg.ecommerce.persistence.entity.UserAccessEntity;
import com.angelfg.ecommerce.service.UserAccessService;
import com.angelfg.ecommerce.service.dto.UserAccessDTO;
import com.angelfg.ecommerce.service.dto.UserAccessResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-access")
public class UserAccessController {

    private final Logger log = LoggerFactory.getLogger(UserAccessController.class);

    @Autowired
    private UserAccessService userAccessService;

    public UserAccessController() {}

    @PostMapping
    public ResponseEntity<UserAccessResponseDTO> add(@RequestBody UserAccessDTO userAccessDTO) {
        String path = "/api/user-access";
        return ResponseEntity.status(HttpStatus.CREATED).body(userAccessService.save(userAccessDTO, path));
    }

    @GetMapping
    public ResponseEntity<List<UserAccessEntity>> getAll() {
        String path = "/api/user-access";
        return ResponseEntity.status(HttpStatus.OK).body(userAccessService.getAll(path));
    }

}
