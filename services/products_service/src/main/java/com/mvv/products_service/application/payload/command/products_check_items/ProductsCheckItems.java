package com.mvv.products_service.application.payload.command.products_check_items;

import com.mvv.products_service.application.payload.common.Card;
import com.mvv.products_service.application.payload.common.Customer;
import com.mvv.products_service.application.payload.common.items.ItemsSolicited;

import java.util.List;
import java.util.UUID;

public record ProductsCheckItems(
        UUID requestId,
        Customer customer,
        Card card,
        List<ItemsSolicited> items
) {
}
