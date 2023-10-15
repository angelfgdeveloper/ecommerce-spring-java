package com.angelfg.ecommerce.service.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserAccessDTO {
    private Long idRole;
    private Long idPrivilege;
    private Long idUser;
}
