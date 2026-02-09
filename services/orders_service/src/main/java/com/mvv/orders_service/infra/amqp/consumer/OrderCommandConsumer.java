package com.mvv.orders_service.infra.amqp.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.orders_service.application.payload.command.orders_cancel.OrdersCancel;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import com.mvv.orders_service.application.payload.command.orders_create.OrdersCreate;
import com.mvv.orders_service.application.usecase.CancelOrderUseCase;
import com.mvv.orders_service.application.usecase.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCommandConsumer {

    private final ObjectMapper objectMapper;
    private final CreateOrderUseCase createOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @RabbitListener(queues = "orders.commands.queue")
    public void commandsListener(@Payload String message) {

        System.out.println("Orders: Recebi um comando: " + message);

        try {

            Envelope<JsonNode> envelope = objectMapper.readValue(message, new TypeReference<Envelope<JsonNode>>() {
            });

            switch (envelope.name()) {

                case "command.orders.create" -> {

                    OrdersCreate commandPayload = objectMapper.convertValue(envelope.payload(), OrdersCreate.class);
                    Envelope<OrdersCreate> commandEnvelope = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.correlationId(), envelope.source(), commandPayload
                    );

                    createOrderUseCase.execute(commandEnvelope);

                }

                case "command.orders.cancel" -> {

                    OrdersCancel commandPayload = objectMapper.convertValue(envelope.payload(), OrdersCancel.class);
                    Envelope<OrdersCancel> commandEnvelope = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.correlationId(), envelope.source(), commandPayload
                    );

                    cancelOrderUseCase.execute(commandEnvelope);

                }

            }

        } catch (Exception e) {


        }


    }

}
