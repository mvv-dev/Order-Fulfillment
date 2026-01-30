package com.mvv.cards_service.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record HttpIssueCardDTO(
        @NotNull(message = "Campo Obrigatório")
        @org.hibernate.validator.constraints.UUID
        UUID cardTypeId
) {
}
