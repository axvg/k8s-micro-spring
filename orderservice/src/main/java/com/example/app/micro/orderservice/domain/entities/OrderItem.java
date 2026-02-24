package com.example.app.micro.orderservice.domain.entities;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItem {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
