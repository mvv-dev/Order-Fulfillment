package com.mvv.orders_service.application.payload.event.orders_solicited;

import com.mvv.orders_service.application.payload.common.Card;
import com.mvv.orders_service.application.payload.common.Customer;

import java.util.List;
import java.util.UUID;

public record OrdersSolicited(
        UUID requestId,
        Customer customer,
        Card card,
        List<ItemsSolicited> items
) {
}
