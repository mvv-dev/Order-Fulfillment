package com.mvv.cards_service.application.payload.common.envelope;

import com.mvv.cards_service.application.payload.common.MessageType;

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
