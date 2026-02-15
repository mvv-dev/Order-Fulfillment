package com.mvv.products_service.application.payload.event.products_items_released;

import java.util.UUID;

public record ItemsReleased(
        UUID productId,
        String name,
        Integer quantityLeft
) {
}
