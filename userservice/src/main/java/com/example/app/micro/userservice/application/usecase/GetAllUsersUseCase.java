package com.example.app.micro.userservice.application.usecase;

import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Obtener todos los usuarios
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllUsersUseCase {

    private final UserRepository userRepository;

    public List<User> execute() {
        log.debug("Executing GetAllUsersUseCase");
        return userRepository.findAll();
    }
}
