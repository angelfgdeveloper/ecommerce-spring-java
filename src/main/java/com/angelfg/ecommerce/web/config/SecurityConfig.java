package com.angelfg.ecommerce.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Proteccion anti-ataques de sitios no seguros
                .cors(Customizer.withDefaults())
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // Permita todos los GET sin auth basic
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() // Permita todos especificos GET sin auth basic
                .requestMatchers(HttpMethod.PUT).denyAll() // Deniega todos los metodos PUT
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults()); // base64 => user:password

        return http.build();
    }

    // "/api/*" => permite hasta un nivel ejemplo http://localhost:8080/api/users
    // "/api/**" => permite consumir todos http://localhost:8080/api/products/list?page=0&elements=1

}
