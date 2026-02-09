package com.mvv.saga_service.application.contracts.common;

public record ReservationError(
        String item,
        String code,
        String message,
        Integer available
) {
}
