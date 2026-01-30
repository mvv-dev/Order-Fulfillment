package com.mvv.cards_service.application.exception.dto;

import java.util.List;

public record UnprocessableEntityError(
        int status,
        String message,
        List<FieldErrorDTO> errors
) {
}
