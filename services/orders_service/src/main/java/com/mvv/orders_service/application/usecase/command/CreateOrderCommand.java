package com.mvv.orders_service.application.usecase.command;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CreateOrderCommand {

    private final UUID orderId;
    private final UUID keycloakUserId;
    private final UUID cardId;
    private final List<CreateOrderItemCommand> items;

    public CreateOrderCommand(UUID keycloakUserId, UUID cardId, List<CreateOrderItemCommand> items) {

        this.orderId = UUID.randomUUID();
        this.keycloakUserId = keycloakUserId;
        this.cardId = cardId;
        this.items = items;

    }


}
