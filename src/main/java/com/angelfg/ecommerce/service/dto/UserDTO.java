package com.angelfg.ecommerce.service.dto;

import com.angelfg.ecommerce.service.exception.CustomException;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static com.angelfg.ecommerce.util.Validations.isValidEmail;

@Data
@ToString
public class UserDTO {

    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static void validUserDTO(UserDTO userDTO, String path) {
        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            throw new CustomException("El nombre no puede estar en blanco", HttpStatus.BAD_REQUEST, path);
        }

        if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) {
            throw new CustomException("El primer nombre no puede estar en blanco", HttpStatus.BAD_REQUEST, path);
        }

        if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) {
            throw new CustomException("El apellido no puede estar en blanco", HttpStatus.BAD_REQUEST, path);
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new CustomException("El email no puede estar en blanco", HttpStatus.BAD_REQUEST, path);
        }

        if (!isValidEmail(userDTO.getEmail())) {
            throw new CustomException("El email no es válido", HttpStatus.BAD_REQUEST, path);
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().length() < 8) {
            throw new CustomException("La contraseña debe tener al menos 8 caracteres", HttpStatus.BAD_REQUEST, path);
        }
    }

}
