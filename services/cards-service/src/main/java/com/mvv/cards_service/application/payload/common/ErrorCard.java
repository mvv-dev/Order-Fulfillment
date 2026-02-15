package com.mvv.cards_service.application.payload.common;

public record ErrorCard(
        String error,
        String message
) {
}
