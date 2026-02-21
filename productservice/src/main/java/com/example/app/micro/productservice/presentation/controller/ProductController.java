package com.example.app.micro.productservice.presentation.controller;

import com.example.app.micro.productservice.application.service.ProductApplicationService;
import com.example.app.micro.productservice.domain.model.Product;
import com.example.app.micro.productservice.presentation.dto.CreateProductRequest;
import com.example.app.micro.productservice.presentation.dto.ProductResponse;
import com.example.app.micro.productservice.presentation.dto.UpdateProductRequest;
import com.example.app.micro.productservice.presentation.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST de Productos
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductApplicationService productApplicationService;

    // Mapper para convertir entre DTOs de presentación y modelo de dominio
    private final ProductDtoMapper productDtoMapper;

    /**
     * Obtiene todos los productos
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("REST request to get all products");
        List<Product> products = productApplicationService.getAllProducts();
        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    /**
     * Obtiene productos disponibles (stock > 0)
     */
    @GetMapping("/available")
    public ResponseEntity<List<ProductResponse>> getAvailableProducts() {
        log.info("REST request to get available products");
        List<Product> products =
            productApplicationService.getAvailableProducts();
        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    /**
     * Obtiene un producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
        @PathVariable Long id
    ) {
        log.info("REST request to get product by id: {}", id);
        Product product = productApplicationService.getProductById(id);
        return ResponseEntity.ok(productDtoMapper.toResponse(product));
    }

    /**
     * Obtiene productos por usuario creador
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponse>> getProductsByUser(
        @PathVariable Long userId
    ) {
        log.info("REST request to get products by user: {}", userId);
        List<Product> products = productApplicationService.getProductsByUser(
            userId
        );
        return ResponseEntity.ok(productDtoMapper.toResponseList(products));
    }

    /**
     * Crea un nuevo producto
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
        @Valid @RequestBody CreateProductRequest request
    ) {
        log.info("REST request to create product: {}", request.getName());
        Product product = productDtoMapper.toDomain(request);
        Product createdProduct = productApplicationService.createProduct(
            product
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
            productDtoMapper.toResponse(createdProduct)
        );
    }

    /**
     * Actualiza un producto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody UpdateProductRequest request
    ) {
        log.info("REST request to update product with id: {}", id);
        Product product = productDtoMapper.toDomain(request);
        Product updatedProduct = productApplicationService.updateProduct(
            id,
            product
        );
        return ResponseEntity.ok(productDtoMapper.toResponse(updatedProduct));
    }

    /**
     * Elimina un producto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("REST request to delete product with id: {}", id);
        productApplicationService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint de salud
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok(
            "Product Service running with Clean Architecture!"
        );
    }
}
