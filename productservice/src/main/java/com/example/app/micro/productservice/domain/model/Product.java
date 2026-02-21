package com.example.app.micro.productservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private LocalDateTime updatetAt;

    private User createdByUser;

    public boolean isValid() {
        return (
            name != null &&
            !name.trim().isEmpty() &&
            price != null &&
            price.compareTo(BigDecimal.ZERO) >= 0 &&
            stock != null &&
            stock >= 0
        );
    }

    public boolean isAvailable() {
        return stock != null && stock > 0;
    }

    public void reduceStock(int qty) {
        if (stock == null || stock < qty) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        this.stock -= qty;
    }

    public void increateStock(int qty) {
        if (qty < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.stock = (this.stock == null ? 0 : this.stock) + qty;
    }
}
