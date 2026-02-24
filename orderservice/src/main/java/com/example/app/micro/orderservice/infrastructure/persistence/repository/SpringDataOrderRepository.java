package com.example.app.micro.orderservice.infrastructure.persistence.repository;

import com.example.app.micro.orderservice.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
    List<OrderEntity> findAllByUserId(Long userId);
}
