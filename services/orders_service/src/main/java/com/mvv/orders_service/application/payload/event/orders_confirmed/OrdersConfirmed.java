package com.mvv.orders_service.application.payload.event.orders_confirmed;

import com.mvv.orders_service.application.payload.common.ItemsReserved;
import com.mvv.orders_service.application.payload.common.StatusPayment;
import com.mvv.orders_service.application.payload.common.Card;
import com.mvv.orders_service.application.payload.common.Customer;
import com.mvv.orders_service.application.payload.common.StatusOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrdersConfirmed(
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations,
        StatusPayment statusPayment,
        BigDecimal totalDebited
) {
}
