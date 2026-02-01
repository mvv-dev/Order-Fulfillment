package com.mvv.products_service.infra.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity {

    @Id
    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer quantity_left;

}
