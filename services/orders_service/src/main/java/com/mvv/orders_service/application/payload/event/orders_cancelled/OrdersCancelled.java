package com.mvv.orders_service.application.payload.event.orders_cancelled;

import com.mvv.orders_service.application.payload.common.ReservationError;
import com.mvv.orders_service.application.payload.common.StatusOrder;

import java.util.List;
import java.util.UUID;

public record OrdersCancelled(
        UUID requestId,
        String reason,
        List<ReservationError> errors,
        StatusOrder status
) {
}
