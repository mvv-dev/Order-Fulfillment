package com.mvv.payments_service.domain.model;

import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Payment {

    private final UUID id;
    private final UUID orderId;
    private final UUID keycloakUserId;
    private final UUID cardId;
    private final BigDecimal amount;
    @Setter
    private StatusPayment status;

    private Payment(UUID id, UUID orderId, BigDecimal amount, UUID keycloakUserId, UUID cardId, StatusPayment status) {

        this.id = Objects.requireNonNull(id, "Id must not be null");
        this.orderId = Objects.requireNonNull(orderId, "Order id must not be null");
        this.amount = Objects.requireNonNull(amount, "Amount must not be null");
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId, "Keycloak user id must not be null");
        this.cardId = Objects.requireNonNull(cardId, "Card id must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Order amount must be higher than 0");
        }

    }

    public Payment(UUID orderId, UUID keycloakUserId, UUID cardId, BigDecimal amount) {

        this.id = UUID.randomUUID();
        this.status = StatusPayment.PENDING;
        this.orderId = Objects.requireNonNull(orderId, "Order id must not be null");
        this.amount = Objects.requireNonNull(amount, "Amount must not be null");
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId, "Keycloak user id must not be null");
        this.cardId = Objects.requireNonNull(cardId, "Card id must not be null");

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Order amount must be higher than 0");
        }

    }

    public static Payment restore(UUID id, UUID orderId, BigDecimal amount, UUID keycloakUserId, UUID cardId,
                                  StatusPayment status) {

        return new Payment(id, orderId, amount, keycloakUserId, cardId, status);

    }

    public void update(StatusPayment status) {

        if (status == null) {
            throw new RuntimeException("Status to update must no be null");
        }

        this.status = status;

    }

}
