package com.angelfg.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class UserWithTokenResponseDTO {
    private UserResponseDTO user;
    private String token;
}
