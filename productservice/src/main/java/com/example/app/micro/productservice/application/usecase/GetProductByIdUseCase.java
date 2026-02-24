package com.example.app.micro.productservice.application.usecase;

import com.example.app.micro.productservice.domain.exception.ProductNotFoundException;
import com.example.app.micro.productservice.domain.exception.UserNotFoundException;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.model.User;
import com.example.app.micro.productservice.domain.repository.ProductRepository;
import com.example.app.micro.productservice.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Obtener producto por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductByIdUseCase {

    private final ProductRepository productRepository;

    private final UserClient userClient;

    public Product execute(Long id) {
        log.debug("Executing GetProductByIdUseCase for id: {}", id);

        Product prod = productRepository
            .findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        // --------------------------------------------------------
        // Llama al microservicio user-service
        // --------------------------------------------------------
        // Validar que el usuario existe en userdb
        if (prod.getCreatedBy() != null) {
            User user = userClient.getUserById(prod.getCreatedBy());
            log.info("Fetching user from userdb: {}", user);

            if (user == null) {
                log.warn(
                    "User with id {} not found in userdb",
                    prod.getCreatedBy()
                );
                throw new UserNotFoundException(prod.getCreatedBy());
            }

            prod.setCreatedByUser(user);
        }

        return prod;
    }
}
