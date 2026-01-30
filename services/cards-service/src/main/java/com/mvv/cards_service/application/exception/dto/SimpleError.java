package com.mvv.cards_service.application.exception.dto;

public record SimpleError(
        int status,
        String message
) {
}
