package com.mvv.saga_service.infra.amqp.consumer;

public record Envelope(
        String name
) {
}
