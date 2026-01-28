package com.mvv.cards_service.application.dto;

import com.mvv.cards_service.domain.model.Brand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record HttpCreateCardTypeDTO(
        @NotBlank(message = "Campo Obrigatório")
        @Size(max = 80, message = "Campo excede 80 caracteres")
        String name,
        @NotNull(message = "Campo Obrigatório")
        Brand brand,
        @NotNull(message = "Campo Obrigatório")
        BigDecimal initialBalance

) {
}
