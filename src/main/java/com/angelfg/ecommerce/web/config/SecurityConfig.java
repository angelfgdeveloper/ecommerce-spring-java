package com.angelfg.ecommerce.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .anyRequest()
//                .permitAll();
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults()); // base64 => user:password

        return http.build();
    }

}
