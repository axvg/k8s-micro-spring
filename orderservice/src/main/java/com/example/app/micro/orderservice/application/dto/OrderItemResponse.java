package com.example.app.micro.orderservice.application.dto;

import com.example.app.micro.orderservice.infrastructure.client.dto.ProductDto;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponse {
    private Long id;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
