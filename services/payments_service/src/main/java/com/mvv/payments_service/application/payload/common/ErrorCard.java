package com.mvv.payments_service.application.payload.common;

public record ErrorCard(
        String error,
        String message
) {
}
