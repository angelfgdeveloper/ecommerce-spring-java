package com.angelfg.ecommerce.service.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserResponseDTO {
    private Long idUser;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
}
