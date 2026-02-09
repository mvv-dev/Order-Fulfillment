package com.mvv.saga_service.application.contracts.commands.payload.orders_cancel;

import com.mvv.saga_service.application.contracts.common.ReservationError;

import java.util.List;
import java.util.UUID;

public record OrdersCancel(
        UUID requestId,
        String reason,
        List<ReservationError> errors
) {
}
