package com.mvv.orders_service.application.usecase.command;

import com.mvv.orders_service.application.controller.dto.HttpProductDTO;
import com.mvv.orders_service.domain.model.OrderItem;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID keycloakUserId, UUID cardId, List<CreateOrderItemCommand> items
) {
}
