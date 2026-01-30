package com.mvv.cards_service.application.exception.dto;

public record FieldErrorDTO(
        String field,
        String message
) {
}
