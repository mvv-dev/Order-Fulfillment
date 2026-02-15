package com.mvv.saga_service.application.contracts.events.payload.payment_processed;

public record ErrorCard(
        String error,
        String message
) {
}
