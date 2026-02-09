package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.products_reserve_inventory.ProductsReserveInventory;
import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.application.contracts.common.MessageType;
import com.mvv.saga_service.application.contracts.common.items.ItemsOrder;
import com.mvv.saga_service.application.contracts.events.payload.orders_created.OrdersCreated;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrdersCreatedHandler {

    private final CommandPublisherPort publisherPort;
    private final ObjectMapper objectMapper;

    public void handle (Envelope<JsonNode> envelope) {

        OrdersCreated eventPayload = objectMapper.convertValue(envelope.payload(), OrdersCreated.class);
        List<ItemsOrder> eventPayloadItems = eventPayload.items().stream().map(
                eventItem -> new ItemsOrder(eventItem.productId(), eventItem.name(), eventItem.price(),
                        eventItem.quantityLeft(), eventItem.requestedQuantity())).toList();

        ProductsReserveInventory commandPayload = new ProductsReserveInventory(
                eventPayload.requestId(), new Customer(eventPayload.customer().keycloakUserId()),
                new Card(eventPayload.card().cardId()), eventPayloadItems, eventPayload.amount(), eventPayload.status()
        );

        Envelope<ProductsReserveInventory> commandEnvelope = new Envelope<>(
                UUID.randomUUID(), "command.products.reserve_iventory", MessageType.COMMAND,
                Instant.now(), envelope.correlationId(), envelope.messageId(), "saga-source", commandPayload
        );

        publisherPort.publish(commandEnvelope);

    }

}
