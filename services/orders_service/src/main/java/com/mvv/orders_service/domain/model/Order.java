package com.mvv.orders_service.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Order {

    private final UUID id;
    @Setter
    private Status status;
    @Setter
    private BigDecimal totalAmount;
    private final UUID keycloakUserId;
    private final UUID cardId;
    private final List<OrderItem> items;

    private Order(UUID id, Status status, BigDecimal totalAmount, UUID keycloakUserId, UUID cardId, List<OrderItem> items){

        this.id = Objects.requireNonNull(id, "Id must not be null");
        this.status = Objects.requireNonNull(status, "Status must not be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "Total Amount must not be null");
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId, "User id must not be null");
        this.cardId = Objects.requireNonNull(cardId, "Card id must no be null");
        this.items = Objects.requireNonNull(items, "Items  must not be null");

        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total amount must be higher than 0");
        }

    }

    public Order(UUID orderId, UUID keycloakUserId, UUID cardId, List<OrderItem> items, BigDecimal totalAmount) {

        this.status = Status.PENDING;
        this.id = Objects.requireNonNull(orderId, "OrderId must be not null");
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId, "User id must not be null");
        this.cardId = Objects.requireNonNull(cardId, "Card id must no be null");
        this.items = Objects.requireNonNull(items, "Items  must not be null");
        this.totalAmount = Objects.requireNonNull(totalAmount, "Total Amount  must not be null");

        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total amount must be higher than 0");
        }

    }

    public static Order restore(UUID id, Status status, BigDecimal totalAmount, UUID keycloakUserId,
                                UUID cardId, List<OrderItem> items) {

        return new Order(id, status, totalAmount, keycloakUserId, cardId, items);

    }

    public void cancelOrder() {
        this.status = Status.CANCELLED;
    }

}
