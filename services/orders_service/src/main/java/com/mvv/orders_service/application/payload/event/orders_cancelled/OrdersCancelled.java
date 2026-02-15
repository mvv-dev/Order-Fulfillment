package com.mvv.orders_service.application.payload.event.orders_cancelled;

import com.mvv.orders_service.application.payload.common.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrdersCancelled (
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations,
        StatusPayment statusPayment,
        BigDecimal totalDebited
){
}
