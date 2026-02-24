package com.example.app.micro.userservice.presentation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta de usuario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Boolean enabled;
    private java.util.List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
