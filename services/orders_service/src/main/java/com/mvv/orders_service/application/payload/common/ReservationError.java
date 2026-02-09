package com.mvv.orders_service.application.payload.common;

public record ReservationError(
        String item,
        String code,
        String message,
        Integer available
) {
}
