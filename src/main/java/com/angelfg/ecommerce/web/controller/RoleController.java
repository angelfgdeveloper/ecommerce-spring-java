package com.angelfg.ecommerce.web.controller;

import com.angelfg.ecommerce.service.RoleService;
import com.angelfg.ecommerce.service.dto.RoleDTO;
import com.angelfg.ecommerce.service.dto.RoleResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    public RoleController() {}

    @PostMapping
    public ResponseEntity<RoleResponseDTO> add(@RequestBody RoleDTO roleDTO) {
        String path = "/api/role";
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(roleDTO, path));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll() {
        String path = "/api/role";
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAll(path));
    }

}
