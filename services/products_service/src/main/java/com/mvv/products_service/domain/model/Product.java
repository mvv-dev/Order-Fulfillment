package com.mvv.products_service.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Product {

    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private Integer quantityLeft;
    private Status productStatus;

    private Product(UUID id, String name, BigDecimal price, Integer quantityLeft) {

        this.id = Objects.requireNonNull(id, "Id must not be null");
        this.name = Objects.requireNonNull(name, "Name must no be null");
        this.price = Objects.requireNonNull(price, "Price must not be null");
        this.quantityLeft = Objects.requireNonNull(quantityLeft, "Initial Quantity left must not be null");

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be higher than 0");
        }

        if (quantityLeft < 0) {
            throw new IllegalArgumentException("Initial quantity left must be higher or equal than 0");
        }

        productStatus = quantityLeft > 0 ? Status.AVAILABLE : Status.SOLDOUT;

    }

    public Product(String name, BigDecimal price, Integer quantityLeft) {

        if (name == null || price == null || quantityLeft == null) {
            throw new IllegalArgumentException("Products info must not be null");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be higher than 0");
        }

        if (quantityLeft <= 0) {
            throw new IllegalArgumentException("Initial quantity left must be higher than 0");
        }

        this.id = UUID.randomUUID();
        this.price = price;
        this.quantityLeft = quantityLeft;
        this.name = name;
        this.productStatus = Status.AVAILABLE;

    }

    public static Product restore(UUID id, String name, BigDecimal price, Integer quantity_left) {
        return new Product(id, name, price, quantity_left);
    }

    public void updateQuantity(Integer demand) {

        if (demand == null) {
            throw new IllegalArgumentException("Demand must not be null");
        }

        if (demand > quantityLeft) {
            throw new IllegalArgumentException("Insufficient inventory");
        }

        quantityLeft -= demand;
        if (quantityLeft == 0) productStatus = Status.SOLDOUT;


    }

    public void releaseQuantity(Integer demand) {

        if (demand == null) {
            throw new IllegalArgumentException("Demand must not be null");
        }

        quantityLeft += demand;

    }

}
