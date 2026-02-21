package com.example.app.micro.productservice.application.usecase;

import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.model.User;
import com.example.app.micro.productservice.domain.repository.ProductRepository;
import com.example.app.micro.productservice.infrastructure.client.UserClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Obtener productos por usuario creador
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductsByUserUseCase {

    private final ProductRepository productRepository;
    private final UserClient userClient;

    public List<Product> execute(Long userId) {
        // --------------------------------------------------------
        // Llama al microservicio user-service
        // --------------------------------------------------------
        // Validar que el usuario existe en userdb
        User user = userClient.getUserById(userId);
        log.info("Fetching products for user from userdb: {}", user.getName());

        // TODO : Validar existencia de usuario o lanzar excepcion

        if (user == null) {
            log.warn("User with id {} not found in userdb", userId);
            throw new RuntimeException("User not found");
        }

        log.debug("Executing GetProductsByUserUseCase for userId: {}", userId);
        return productRepository.findByCreatedBy(userId);
    }
}
