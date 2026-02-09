package com.mvv.products_service.application.payload.event.products_inventory_reserved;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemsReserved(
        UUID productId,
        String name,
        Integer reservedQuantity
) {
}
