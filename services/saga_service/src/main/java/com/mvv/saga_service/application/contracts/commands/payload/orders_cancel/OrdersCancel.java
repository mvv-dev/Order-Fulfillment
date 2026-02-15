package com.mvv.saga_service.application.contracts.commands.payload.orders_cancel;

import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.StatusOrder;
import com.mvv.saga_service.application.contracts.events.payload.payment_processed.ErrorCard;
import com.mvv.saga_service.application.contracts.events.payload.payment_processed.StatusPayment;
import com.mvv.saga_service.application.contracts.common.ItemsReserved;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrdersCancel (
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations,
        StatusPayment statusPayment,
        BigDecimal totalDebited,
        List<ErrorCard> errors
){
}
