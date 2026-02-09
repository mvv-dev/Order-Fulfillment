package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.orders_create.OrdersCreate;
import com.mvv.saga_service.application.contracts.common.*;
import com.mvv.saga_service.application.contracts.common.items.ItemsOrder;
import com.mvv.saga_service.application.contracts.events.payload.products_items_checked.ProductsItemsChecked;
import com.mvv.saga_service.application.contracts.common.StatusProduct;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductsItemsCheckedHandler {

    private final ObjectMapper objectMapper;
    private final CommandPublisherPort commandPublisherPort;

    public void handle(Envelope<JsonNode> envelope) {

        ProductsItemsChecked eventPayload = objectMapper.convertValue(envelope.payload(), ProductsItemsChecked.class);

        if (eventPayload.statusProduct().equals(StatusProduct.OK)) {

            BigDecimal amount = BigDecimal.ZERO;
            List<ItemsOrder> eventItems = new ArrayList<>();

            for (var product : eventPayload.itemsOrder()) {
                BigDecimal requestedQuantity = BigDecimal.valueOf(product.requestedQuantity());
                BigDecimal totalPerProduct = product.price().multiply(requestedQuantity);
                amount = amount.add(totalPerProduct);
                eventItems.add(new ItemsOrder(product.productId(), product.name(), product.price(), product.quantityLeft(),
                        product.requestedQuantity()));
            }

            OrdersCreate ordersCreatePayload = new OrdersCreate(eventPayload.requestId(),
                    new Customer(eventPayload.customer().keycloakUserId()), new Card(eventPayload.card().cardId()),
                    eventItems, amount);

            Envelope<OrdersCreate> envelopeOrdersCreate = new Envelope<>(
                    UUID.randomUUID(), "command.orders.create", MessageType.COMMAND, Instant.now(),
                    envelope.correlationId(), envelope.messageId(), "saga-source", ordersCreatePayload
            );

            commandPublisherPort.publish(envelopeOrdersCreate);

        } else {
            System.out.println("FAILED, some producuts does not exist.");
        }

    }

}
