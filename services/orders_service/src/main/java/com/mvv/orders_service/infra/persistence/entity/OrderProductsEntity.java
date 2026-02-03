package com.mvv.orders_service.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_products")
@Getter
@Setter
public class OrderProductsEntity {

    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID rowId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_quantity")
    private Integer productQuantity;

}
