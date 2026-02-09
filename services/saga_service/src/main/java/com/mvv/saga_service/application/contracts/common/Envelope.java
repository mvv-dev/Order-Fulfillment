package com.mvv.saga_service.application.contracts.common;

import java.time.Instant;
import java.util.UUID;

public record Envelope<T>(
        UUID messageId,
        String name,
        MessageType type,
        Instant ocurredAt,
        UUID correlationId,
        UUID causationId,
        String source,
        T payload
){}