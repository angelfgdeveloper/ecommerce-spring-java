package com.angelfg.ecommerce.util;

public class Validaciones {

    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

}
