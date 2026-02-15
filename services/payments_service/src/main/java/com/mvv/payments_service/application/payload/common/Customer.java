package com.mvv.payments_service.application.payload.common;

import java.util.UUID;

public record Customer(
        UUID keycloakUserId
) {
}
