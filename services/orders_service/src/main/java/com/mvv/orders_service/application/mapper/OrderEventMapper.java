package com.mvv.orders_service.application.mapper;

import com.mvv.orders_service.application.usecase.command.CreateOrderCommand;
import com.mvv.orders_service.infra.amqp.dto.Items;
import com.mvv.orders_service.infra.amqp.dto.MessageType;
import com.mvv.orders_service.infra.amqp.dto.OrderSolicitedEventDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class OrderEventMapper {

    public OrderSolicitedEventDTO toOrderSolicitedEventDTO(CreateOrderCommand orderCommand) {

        UUID messageId = UUID.randomUUID();
        String name = "event.orders.solicited";
        MessageType type = MessageType.EVENT;
        Instant ocurredAt = Instant.now();
        UUID correlationId = orderCommand.getOrderId();
        UUID orderId = orderCommand.getOrderId();
        UUID keycloakUserId = orderCommand.getKeycloakUserId();
        UUID cardId = orderCommand.getCardId();
        List<Items> items = orderCommand.getItems().stream().map(
                orderItem -> new Items(orderItem.name(), orderItem.quantity())
        ).toList();

        return new  OrderSolicitedEventDTO(
            messageId, name, type, ocurredAt, correlationId, null, orderId, keycloakUserId, cardId, items
        );



    }

}
