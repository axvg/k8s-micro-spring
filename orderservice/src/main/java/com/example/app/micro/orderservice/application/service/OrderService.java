package com.example.app.micro.orderservice.application.service;

import com.example.app.micro.orderservice.application.dto.OrderRequest;
import com.example.app.micro.orderservice.application.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(Long id);
}
