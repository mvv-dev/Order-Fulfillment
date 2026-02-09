package com.mvv.orders_service.application.payload.common;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemsOrder(
        UUID productId,
        String name,
        BigDecimal price,
        Integer quantityLeft,
        Integer requestedQuantity
) {
}
