package com.angelfg.ecommerce.service.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class RoleResponseDTO {
    private Long idRole;
    private String name;
    private LocalDateTime created_at;
}
