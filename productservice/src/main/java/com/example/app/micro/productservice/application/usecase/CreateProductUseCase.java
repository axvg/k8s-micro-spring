package com.example.app.micro.productservice.application.usecase;

import com.example.app.micro.productservice.domain.exception.InvalidProductDataException;
import com.example.app.micro.productservice.domain.exception.UserNotFoundException;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.model.User;
import com.example.app.micro.productservice.domain.repository.ProductRepository;
import com.example.app.micro.productservice.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Crear un nuevo producto
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final UserClient userClient;

    public Product execute(Product product) {
        log.debug(
            "Executing CreateProductUseCase for product: {}",
            product.getName()
        );

        // Validar datos del producto
        if (!product.isValid()) {
            throw new InvalidProductDataException(
                "Invalid product data. Name, valid price and stock are required."
            );
        }

        // Si viene con un ID de creador, validamos que exista y lo adjuntamos
        if (product.getCreatedBy() != null) {
            User user = userClient.getUserById(product.getCreatedBy());
            if (user == null) {
                log.warn(
                    "User with id {} not found in userdb when creating product",
                    product.getCreatedBy()
                );
                throw new UserNotFoundException(product.getCreatedBy());
            }
            product.setCreatedByUser(user);
        }

        // Guardar producto
        Product savedProduct = productRepository.save(product);
        log.info(
            "Product created successfully with id: {}",
            savedProduct.getId()
        );
        
        // Re-adjuntar el usuario al objeto guardado para la respuesta
        savedProduct.setCreatedByUser(product.getCreatedByUser());

        return savedProduct;
    }
}
