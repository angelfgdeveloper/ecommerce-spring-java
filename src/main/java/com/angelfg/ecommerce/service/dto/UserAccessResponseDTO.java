package com.angelfg.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAccessResponseDTO {
    private Long idUserAccess;
    private RoleResponseDTO role;
    private PrivilegeResponseDTO privilege;
    private UserResponseDTO user;
    private LocalDateTime created_at;
}
