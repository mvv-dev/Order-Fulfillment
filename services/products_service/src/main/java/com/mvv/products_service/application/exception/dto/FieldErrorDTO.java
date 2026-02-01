package com.mvv.products_service.application.exception.dto;

public record FieldErrorDTO(
        String field,
        String message
) {
}
