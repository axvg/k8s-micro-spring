package com.example.app.micro.orderservice.infrastructure.persistence.repository;

import com.example.app.micro.orderservice.domain.entities.Order;
import com.example.app.micro.orderservice.domain.entities.OrderItem;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;
import com.example.app.micro.orderservice.infrastructure.persistence.entity.OrderEntity;
import com.example.app.micro.orderservice.infrastructure.persistence.entity.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapToEntity(order);
        if (order.getItems() != null) {
            entity.getItems().clear();
            for (OrderItem item : order.getItems()) {
                OrderItemEntity itemEntity = mapItemToEntity(item);
                entity.addItem(itemEntity);
            }
        }
        OrderEntity savedEntity = springDataOrderRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return springDataOrderRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return springDataOrderRepository.findByOrderNumber(orderNumber).map(this::mapToDomain);
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return springDataOrderRepository.findAllByUserId(userId).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private OrderEntity mapToEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private OrderItemEntity mapItemToEntity(OrderItem item) {
        return OrderItemEntity.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .build();
    }

    private Order mapToDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .orderNumber(entity.getOrderNumber())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .totalAmount(entity.getTotalAmount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .items(entity.getItems() != null ? entity.getItems().stream().map(this::mapItemToDomain).collect(Collectors.toList()) : null)
                .build();
    }

    private OrderItem mapItemToDomain(OrderItemEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .subtotal(entity.getSubtotal())
                .build();
    }
}
