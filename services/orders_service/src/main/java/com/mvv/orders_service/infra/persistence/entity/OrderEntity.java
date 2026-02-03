package com.mvv.orders_service.infra.persistence.entity;

import com.mvv.orders_service.domain.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "keycloak_user_id")
    private UUID keycloakUserId;

    @Column(name = "card_id")
    private UUID cardId;

}
