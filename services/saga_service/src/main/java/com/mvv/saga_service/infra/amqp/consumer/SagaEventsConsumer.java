package com.mvv.saga_service.infra.amqp.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.handlers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SagaEventsConsumer {

    private final ObjectMapper objectMapper;
    private final OrdersSolicitedHandler orderSolicitedHandler;
    private final ProductsItemsCheckedHandler productsItemsCheckedHandler;
    private final OrdersCreatedHandler ordersCreatedHandler;
    private final ProductsInventoryReservedHandler productsInventoryReservedHandler;
    private final OrdersCancelledHandler ordersCancelledHandler;

    @RabbitListener(queues = "saga.events.queue")
    public void eventsListener(@Payload String message) {

        System.out.println("Saga: Recebi uma mensagem: " + message);

        try {

            Envelope<JsonNode> envelope = objectMapper.readValue(message, new TypeReference<Envelope<JsonNode>>() {
            });

            switch (envelope.name()) {

                case "event.orders.solicited" -> {
                    orderSolicitedHandler.handle(envelope);
                }

                case "event.products.items_checked" -> {
                    productsItemsCheckedHandler.handle(envelope);
                }

                case "event.orders.created" -> {
                    ordersCreatedHandler.handle(envelope);
                }

                case "event.products.inventory_reserved" -> {
                    productsInventoryReservedHandler.handle(envelope);
                }

                case "event.oders.cancelled" -> {
                    ordersCancelledHandler.handler();
                }

            }

        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON: " + e);
        }

    }


}
