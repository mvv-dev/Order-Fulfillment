package com.mvv.orders_service.application.payload.command.orders_cancel;

import com.mvv.orders_service.application.payload.common.ReservationError;

import java.util.List;
import java.util.UUID;

public record OrdersCancel (
        UUID requestId,
        String reason,
        List<ReservationError> errors
){

}
