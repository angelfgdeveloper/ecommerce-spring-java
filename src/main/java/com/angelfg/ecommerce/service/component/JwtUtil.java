package com.angelfg.ecommerce.service.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    private static String SECRET_KEY = "eE3C0mM3rCzEO0FlU1zsz"; // ponerlo en las properties
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String create(String email) {
        Date currentDate = new Date();

        return JWT.create()
                .withSubject(email)
                .withIssuer("com.angelfg.ecommerce") // Quien lo esta integrando
                .withIssuedAt(currentDate) // Fecha actual
                .withExpiresAt(new Date(currentDate.getTime() + TimeUnit.HOURS.toMillis(6))) // expira el token en 6 horas
                .sign(ALGORITHM); // firmar nuestro token
    }

}
