package com.angelfg.ecommerce.web.config;

import com.angelfg.ecommerce.service.component.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableMethodSecurity(securedEnabled = true) // habilitar para poder controlar las anotaciones desde los services
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    private static final String[] excludedAuthPages = {
            "/test/excludedAuthPages",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Proteccion anti-ataques de sitios no seguros
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Se protege para que no tenga una sesion con el jwt
                .authorizeRequests()
//                .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // Permita todos los GET sin auth basic
//                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() // Permita todos especificos GET sin auth basic
                .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("ADMIN", "USER") // Permita consumir con los ROLES de admin y user
                .requestMatchers(HttpMethod.POST, "/api/products").permitAll() //.hasRole("ADMIN") // Solo el admin puede crear products
                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN") // Solo puede actualizar con el Role de Admin
                .requestMatchers(HttpMethod.DELETE).denyAll() // Deniega todos los metodos DELETE
                .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/role").hasAuthority("ACCESS_VIEW_ROLES") // Es importante donde esta ubicado, porque si hay un admin antes que tenga privilegios con una ruta similar seguira entrando
                .requestMatchers(HttpMethod.GET, "/api/privilege").hasAuthority("ACCESS_VIEW_PRIVILEGES")
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
//                .requestMatchers("/doc/**").permitAll()
                .requestMatchers(excludedAuthPages).permitAll()
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic(Customizer.withDefaults()); // base64 => user:password
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Agrega un filtro antes que otro para el jwt

        return http.build();
    }

    // "/api/*" => permite hasta un nivel ejemplo http://localhost:8080/api/users
    // "/api/**" => permite consumir todos http://localhost:8080/api/products/list?page=0&elements=1

    /**
     * Generamos usuarios en memoria y ya no generara la contraseña autogenerada
     * @return
     */
//    @Bean
//    public UserDetailsService memoryUsers() {
//        // Spring necesita que los password esten hasehadas
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("user123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
