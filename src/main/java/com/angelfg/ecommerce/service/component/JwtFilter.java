package com.angelfg.ecommerce.service.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Validar que sea un Header Authorization valido
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")) {
            // Sino contiene el Authorization que continue con el flujo o si es vacio o si no contiene el Bearer
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Validar que el JWT sea valido
        String token = authHeader.split(" ")[1].trim(); // obtenemos el segundo valor token Bearer sdjkfhdsjlfh

        if (!jwtUtil.isValidToken(token)) {
            // Sino es valido que continue con el flujo
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Cargar el usuario del UserDetailsService
        String email = jwtUtil.getEmail(token);
        User user = (User) userDetailsService.loadUserByUsername(email);

        // 4. Cargar al usuario en el contexto de seguridad
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.debug("authenticationToken => " + authenticationToken);
        filterChain.doFilter(request, response);
    }

}
