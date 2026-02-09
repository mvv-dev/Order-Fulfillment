package com.mvv.products_service.application.payload.event.products_inventory_reserved;

public record ReservationError(
        String item,
        String code,
        String message,
        Integer available
) {
}
