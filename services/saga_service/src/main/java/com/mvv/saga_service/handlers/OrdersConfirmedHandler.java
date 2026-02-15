package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.products_release_items.ProductsReleaseItems;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.application.contracts.events.payload.orders_confirmed.OrdersConfirmed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrdersConfirmedHandler {

    private final ObjectMapper objectMapper;

    public void handle(Envelope<JsonNode> envelope) {

        OrdersConfirmed eventPayload = objectMapper.convertValue(envelope.payload(), OrdersConfirmed.class);
        log.info("Order was sucessfully confirmed: {}", eventPayload);


    }

}
