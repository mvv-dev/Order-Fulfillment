package com.mvv.orders_service.application.payload.common.envelope;

import com.mvv.orders_service.application.payload.common.MessageType;

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
) {

    public static <T> Envelope<T> replyFrom(
            Envelope<?> in,
            String outName,
            MessageType type,
            T payload,
            String source
    ) {
        return new Envelope<>(
                UUID.randomUUID(),
                outName,
                type,
                Instant.now(),
                in.correlationId,
                in.messageId,
                source,
                payload
        );
    }

}
