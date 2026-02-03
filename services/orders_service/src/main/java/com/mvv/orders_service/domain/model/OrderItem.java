package com.mvv.orders_service.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
public class OrderItem {

    private UUID productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    private OrderItem(UUID productId, String name, BigDecimal price, Integer quantity) {

        this.productId = Objects.requireNonNull(productId);
        this.name = Objects.requireNonNull(name);
        this.price = Objects.requireNonNull(price);
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be higher 0");
        if (price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Price must be higher than 0");
        this.quantity = quantity;

    }

    public static OrderItem create(UUID productId, String name, BigDecimal price, Integer quantity) {
        return new OrderItem(productId, name, price, quantity);
    }

    public static OrderItem restore(UUID productId, String name, BigDecimal price, Integer quantity) {
        return new OrderItem(productId, name, price, quantity);
    }



}
