package com.mvv.saga_service.application.contracts.common;

import java.util.UUID;

public record Customer(
        UUID keycloakUserId
) {
}
