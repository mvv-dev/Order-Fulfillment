package com.mvv.saga_service.application.contracts.commands.payload.orders_invalidate;

import com.mvv.saga_service.application.contracts.common.ReservationError;

import java.util.List;
import java.util.UUID;

public record OrdersInvalidate(
        UUID requestId,
        String reason,
        List<ReservationError> errors
) {
}
