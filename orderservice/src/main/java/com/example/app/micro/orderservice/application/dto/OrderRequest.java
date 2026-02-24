package com.example.app.micro.orderservice.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    @NotNull(message = "userId is required")
    private Long userId;
    
    @NotEmpty(message = "items are required")
    private List<OrderItemRequest> items;
}
