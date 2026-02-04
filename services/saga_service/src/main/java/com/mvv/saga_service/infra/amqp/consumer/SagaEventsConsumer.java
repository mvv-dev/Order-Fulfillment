package com.mvv.saga_service.infra.amqp.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.SagaEventHandler;
import com.mvv.saga_service.contratcts.events.OrderSolicited;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SagaEventsConsumer {

    private final ObjectMapper objectMapper;
    private final SagaEventHandler handler;
    private final Queue queue;

    @RabbitListener(queues = "saga.events.queue")
    public void onMessage(@Payload String message) {

        System.out.println("Recebi a mensagem, vou tentar prosseguir");

        try {
            OrderSolicited orderSolicited = objectMapper.readValue(message, OrderSolicited.class);

            switch (orderSolicited.name()) {

                case "event.orders.solicited" -> {
                    handler.onOrderSolicited(orderSolicited);
                }
                default -> throw new IllegalArgumentException("Unknown message name: " + orderSolicited.name());

            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to consume saga event", e);
        }

    }



}
