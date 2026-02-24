package com.example.app.micro.orderservice.domain.entities;

import com.example.app.micro.orderservice.domain.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {
    private Long id;
    private String orderNumber;
    private Long userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItem> items;
}
