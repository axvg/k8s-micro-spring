package com.example.app.micro.userservice.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para operaciones de autenticación (Sesión 1 - Basic Auth testing)
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    /**
     * Endpoint público (o protegido por Basic según config) para probar el login.
     * Como estamos en Basic Auth, Spring Security intercepta el request antes.
     * Si llega aquí, es porque las credenciales en el header Authorization son válidas.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(Authentication authentication) {
        log.info("Login exitoso para usuario: {}", authentication.getName());
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Autenticación exitosa (Basic Auth)");
        response.put("username", userDetails.getUsername());
        response.put("authorities", userDetails.getAuthorities());
        
        return ResponseEntity.ok(response);
    }
}
