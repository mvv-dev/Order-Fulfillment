package com.mvv.orders_service.infra.amqp.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.orders_service.application.payload.command.orders_cancel.OrdersCancel;
import com.mvv.orders_service.application.payload.command.orders_confirm.OrdersConfirm;
import com.mvv.orders_service.application.payload.command.orders_invalidate.OrdersInvalidate;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import com.mvv.orders_service.application.payload.command.orders_create.OrdersCreate;
import com.mvv.orders_service.application.usecase.CancelOrderUseCase;
import com.mvv.orders_service.application.usecase.ConfirmOrderUseCase;
import com.mvv.orders_service.application.usecase.InvalidateOrderUseCase;
import com.mvv.orders_service.application.usecase.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCommandConsumer {

    private final ObjectMapper objectMapper;
    private final CreateOrderUseCase createOrderUseCase;
    private final InvalidateOrderUseCase invalidateOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @RabbitListener(queues = "orders.commands.queue")
    public void commandsListener(@Payload String message) {

        log.info("A command was recieved: {}", message);

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

                case "command.orders.invalidate" -> {

                    OrdersInvalidate commandPayload = objectMapper.convertValue(envelope.payload(), OrdersInvalidate.class);
                    Envelope<OrdersInvalidate> commandEnvelope = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.correlationId(), envelope.source(), commandPayload
                    );

                    invalidateOrderUseCase.execute(commandEnvelope);

                }

                case "command.orders.confirm" -> {

                    OrdersConfirm commandPayload = objectMapper.convertValue(envelope.payload(), OrdersConfirm.class);
                    Envelope<OrdersConfirm> commandEnvelope = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.correlationId(), envelope.source(), commandPayload
                    );

                    confirmOrderUseCase.execute(commandEnvelope);

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

            log.error("Error converting message payload");
            throw new RuntimeException("Error converting message payload");

        }


    }

}
