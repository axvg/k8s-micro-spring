package com.example.app.micro.orderservice.application.service;

import com.example.app.micro.orderservice.application.dto.OrderItemRequest;
import com.example.app.micro.orderservice.application.dto.OrderItemResponse;
import com.example.app.micro.orderservice.application.dto.OrderRequest;
import com.example.app.micro.orderservice.application.dto.OrderResponse;
import com.example.app.micro.orderservice.domain.entities.Order;
import com.example.app.micro.orderservice.domain.entities.OrderItem;
import com.example.app.micro.orderservice.domain.enums.OrderStatus;
import com.example.app.micro.orderservice.domain.exception.BusinessException;
import com.example.app.micro.orderservice.domain.exception.ResourceNotFoundException;
import com.example.app.micro.orderservice.domain.repository.OrderRepository;
import com.example.app.micro.orderservice.infrastructure.client.ProductClient;
import com.example.app.micro.orderservice.infrastructure.client.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            ProductDto product = productClient.getProductById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemRequest.getProductId()));
            
            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            OrderItem orderItem = OrderItem.builder()
                    .productId(product.getId())
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .subtotal(subtotal)
                    .build();
            
            orderItems.add(orderItem);
        }

        String orderNumber = "ORD-" + java.time.Year.now().getValue() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .userId(request.getUserId())
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .items(orderItems)
                .build();

        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream().map(item -> {
            ProductDto productDto = productClient.getProductById(item.getProductId()).orElse(null);
            return OrderItemResponse.builder()
                    .id(item.getId())
                    .product(productDto)
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .subtotal(item.getSubtotal())
                    .build();
        }).collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }
}
