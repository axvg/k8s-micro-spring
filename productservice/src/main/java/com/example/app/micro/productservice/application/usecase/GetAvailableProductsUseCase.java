package com.example.app.micro.productservice.application.usecase;

import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.domain.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Obtener productos disponibles (stock > 0)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetAvailableProductsUseCase {

    private final ProductRepository productRepository;

    public List<Product> execute() {
        log.debug("Executing GetAvailableProductsUseCase");
        return productRepository.findAvailableProducts();
    }
}
