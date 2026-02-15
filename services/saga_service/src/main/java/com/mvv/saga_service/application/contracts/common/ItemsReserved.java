package com.mvv.saga_service.application.contracts.common;

import java.util.UUID;

public record ItemsReserved(
        UUID productId,
        String name,
        Integer reservedQuantity
) {
}
