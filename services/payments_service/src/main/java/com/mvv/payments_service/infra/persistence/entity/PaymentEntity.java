package com.mvv.payments_service.infra.persistence.entity;

import com.mvv.payments_service.domain.model.StatusPayment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class PaymentEntity {

    @Id
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "keycloak_user_id")
    private UUID keycloakUserId;

    @Column(name = "card_id")
    private UUID cardId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private StatusPayment status;

}
