package com.mvv.saga_service.application.contracts.common.items;

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
