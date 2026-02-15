package com.mvv.saga_service.application.contracts.commands.payload.payments_process;

import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.StatusOrder;
import com.mvv.saga_service.application.contracts.common.ItemsReserved;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PaymentsProcess(
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations
) {
}
