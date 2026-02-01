package com.mvv.products_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record HttpSaveProductDTO(
        @NotBlank(message = "Required field")
        @Size(max = 100, message = "The field exceeds 100 characters.")
        String name,
        @NotNull(message = "Required field")
        BigDecimal price,
        @NotNull(message = "Required field")
        Integer quantityLeft
) {
}
