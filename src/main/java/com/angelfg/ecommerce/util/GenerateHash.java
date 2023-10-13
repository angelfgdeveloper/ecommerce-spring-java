package com.angelfg.ecommerce.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GenerateHash {

    /**
     * Convierte el texto en un bcrypt (contraseña hasheada)
     * encode => codificas
     * matches => verifica si la contraseña es igual a la contraseña hasheada esperada
     * @return
     */
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
