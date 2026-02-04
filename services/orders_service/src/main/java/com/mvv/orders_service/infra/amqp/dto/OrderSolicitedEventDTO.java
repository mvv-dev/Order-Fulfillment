package com.mvv.orders_service.infra.amqp.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderSolicitedEventDTO(
        UUID messageId,
        String name,
        MessageType type,
        Instant ocurredAt,
        UUID correlationId,
        UUID causationId,
        UUID orderId,
        UUID keycloakUserId,
        UUID cardId,
        List<Items> items
) {
}
