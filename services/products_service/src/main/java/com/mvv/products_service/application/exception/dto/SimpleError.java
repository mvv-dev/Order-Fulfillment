package com.mvv.products_service.application.exception.dto;

public record SimpleError(
        int status,
        String message
) {
}
