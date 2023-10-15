package com.angelfg.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessRolesAndPrivilegesDTO {
    private String[] roles;
    private String[] privileges;
}
