package com.mvv.products_service.application.exception.dto;

import java.util.List;

public record UnprocessableEntityError(
        int status,
        String message,
        List<FieldErrorDTO> errors
) {
}
