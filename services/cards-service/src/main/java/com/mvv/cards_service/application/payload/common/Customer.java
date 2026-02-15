package com.mvv.cards_service.application.payload.common;

import java.util.UUID;

public record Customer(
        UUID keycloakUserId
) {
}
