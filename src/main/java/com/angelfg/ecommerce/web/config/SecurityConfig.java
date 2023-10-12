package com.angelfg.ecommerce.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Proteccion anti-ataques de sitios no seguros
                .cors(Customizer.withDefaults())
                .authorizeRequests()
//                .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // Permita todos los GET sin auth basic
//                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() // Permita todos especificos GET sin auth basic
                .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("ADMIN", "USER")// Permita consumir con los ROLES de admin y user
                .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN") // Solo el admin puede crear products
                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN") // Solo puede actualizar con el Role de Admin
                .requestMatchers(HttpMethod.DELETE).denyAll() // Deniega todos los metodos DELETE
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(Customizer.withDefaults()); // base64 => user:password

        return http.build();
    }

    // "/api/*" => permite hasta un nivel ejemplo http://localhost:8080/api/users
    // "/api/**" => permite consumir todos http://localhost:8080/api/products/list?page=0&elements=1

    /**
     * Generamos usuarios en memoria y ya no generara la contraseña autogenerada
     * @return
     */
    @Bean
    public UserDetailsService memoryUsers() {
        // Spring necesita que los password esten hasehadas

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    /**
     * Convierte el texto en un bcrypt (contraseña hasheada)
     * encode => codificas
     * matches => verifica si la contraseña es igual a la contraseña hasheada esperada
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
