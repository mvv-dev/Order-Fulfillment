package com.mvv.saga_service.contratcts.events;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderSolicited(
        UUID messageId,
        String name,
        MessageTypeOrderSolicited type,
        Instant ocurredAt,
        UUID correlationId,
        UUID causationId,
        UUID orderId,
        UUID keycloakUserId,
        UUID cardId,
        List<ItemsOrderSolicited> items
) {
}
