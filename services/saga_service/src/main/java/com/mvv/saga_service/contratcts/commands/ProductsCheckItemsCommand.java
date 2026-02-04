package com.mvv.saga_service.contratcts.commands;


import com.mvv.saga_service.contratcts.MessageType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ProductsCheckItemsCommand(
        UUID messageId,
        String name,
        MessageType type,
        UUID correlationId,
        UUID orderId,
        UUID causationId,
        Instant ocurredAt,
        List<ItemsProductCheck> items

) {
}
