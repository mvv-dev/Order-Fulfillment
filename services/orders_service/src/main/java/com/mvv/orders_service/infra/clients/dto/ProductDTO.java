package com.mvv.orders_service.infra.clients.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name,
        BigDecimal price,
        Integer quantityLeft
) {
}
