package com.mvv.saga_service.application.contracts.events.payload.products_items_checked;

public record ProductError(
        String item,
        String code,
        String message
) {
}
