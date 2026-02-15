package com.mvv.orders_service.application.payload.common;

import java.util.UUID;

public record ItemsReserved(
        UUID productId,
        String name,
        Integer reservedQuantity
) {
}
