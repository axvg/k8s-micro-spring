package com.example.app.micro.orderservice.infrastructure.client;

import com.example.app.micro.orderservice.infrastructure.client.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public Optional<ProductDto> getProductById(Long id) {
        try {
            String url = productServiceUrl + "/api/products/" + id;
            ResponseEntity<ProductDto> response = restTemplate.getForEntity(url, ProductDto.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (Exception e) {
            // Log real error in production
            return Optional.empty();
        }
        return Optional.empty();
    }
}
