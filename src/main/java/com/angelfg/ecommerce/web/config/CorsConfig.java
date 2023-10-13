package com.angelfg.ecommerce.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    /**
     * Configuraciones de cors para permitir o denegar los origines o dominios que no esten en nuestro control
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Permitir peticiones desde este origen
        corsConfiguration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200"
        ));

        // Desde que metodos permito que puedan utilizarse desde otro dominio
        corsConfiguration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE"
        ));

        // Que Headers quiero que lleguen
        corsConfiguration.setAllowedHeaders(
            Arrays.asList("*") // Permite todos los encabezados que vengan en las rutas
        );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
