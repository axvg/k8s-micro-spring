package com.example.app.micro.orderservice.domain.repository;

import com.example.app.micro.orderservice.domain.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findAllByUserId(Long userId);
}
