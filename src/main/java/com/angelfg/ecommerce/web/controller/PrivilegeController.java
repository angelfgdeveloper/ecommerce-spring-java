package com.angelfg.ecommerce.web.controller;

import com.angelfg.ecommerce.service.PrivilegeService;
import com.angelfg.ecommerce.service.dto.PrivilegeDTO;
import com.angelfg.ecommerce.service.dto.PrivilegeResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privilege")
public class PrivilegeController {

    private final Logger log = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeService;

    public PrivilegeController() {}

    @PostMapping
    public ResponseEntity<PrivilegeResponseDTO> add(@RequestBody PrivilegeDTO privilegeDTO) {
        String path = "/api/privilege";
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.save(privilegeDTO, path));
    }

    @GetMapping
    public ResponseEntity<List<PrivilegeResponseDTO>> getAll() {
        String path = "/api/privilege";
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.getAll(path));
    }


}
