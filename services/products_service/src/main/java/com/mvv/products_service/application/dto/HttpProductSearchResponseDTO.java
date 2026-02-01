package com.mvv.products_service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record HttpProductSearchResponseDTO(
        UUID id,
        String name,
        BigDecimal price,
        Integer quantityLeft
) {
}
