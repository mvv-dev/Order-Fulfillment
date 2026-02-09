package com.mvv.saga_service.application.contracts.events.payload.products_items_checked;

import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.StatusProduct;
import com.mvv.saga_service.application.contracts.common.items.ItemsOrder;

import java.util.List;
import java.util.UUID;

public record ProductsItemsChecked(
        UUID requestId,
        Customer customer,
        Card card,
        StatusProduct statusProduct,
        List<ItemsOrder> itemsOrder,
        List<ProductError> errors
) {
}
