package com.example.app.micro.userservice.application.usecase;

import com.example.app.micro.userservice.domain.exception.DuplicateEmailException;
import com.example.app.micro.userservice.domain.exception.InvalidUserDataException;
import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Crear un nuevo usuario
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User execute(User user) {
        log.debug("Executing CreateUserUseCase for email: {}", user.getEmail());

        // Validar datos del usuario
        if (!user.isValid()) {
            throw new InvalidUserDataException(
                "Invalid user data. Name and valid email are required."
            );
        }

        // Verificar que el email no exista
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(user.getEmail());
        }

        // Hashear el password
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            throw new InvalidUserDataException("Password is required to create a user");
        }

        // Guardar usuario
        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {}", savedUser.getId());

        return savedUser;
    }
}
