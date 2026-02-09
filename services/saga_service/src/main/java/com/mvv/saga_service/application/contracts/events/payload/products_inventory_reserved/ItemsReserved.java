package com.mvv.saga_service.application.contracts.events.payload.products_inventory_reserved;

import java.util.UUID;

public record ItemsReserved(
        UUID productId,
        String name,
        Integer reservedQuantity
) {
}
