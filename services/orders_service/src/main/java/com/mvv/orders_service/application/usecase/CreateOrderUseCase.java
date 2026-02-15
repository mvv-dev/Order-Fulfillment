package com.mvv.orders_service.application.usecase;

import com.mvv.orders_service.application.payload.common.*;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import com.mvv.orders_service.application.payload.command.orders_create.OrdersCreate;
import com.mvv.orders_service.application.payload.event.orders_created.OrdersCreated;
import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.model.OrderItem;
import com.mvv.orders_service.infra.amqp.publisher.OrderEventPublisher;
import com.mvv.orders_service.infra.persistence.adapter.OrderRepositoryAdapter;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrderUseCase {

    private final OrderRepositoryAdapter orderRepositoryAdapter;
    private final OrderEventPublisher orderEventPublisher;

    public void execute(Envelope<OrdersCreate> envelope) {

        OrdersCreate commandPayload = envelope.payload();

        List<OrderItem> ordersItems = commandPayload.items().stream()
                .map(envolopeItem -> OrderItem.create(envolopeItem.productId(), envolopeItem.name()
                        , envolopeItem.price(), envolopeItem.requestedQuantity())).toList();

        Order orderToSave = new Order(commandPayload.requestId(),commandPayload.customer().keycloakUserId(),
                commandPayload.card().cardId(), ordersItems, commandPayload.amount());



        Order orderSaved = orderRepositoryAdapter.save(orderToSave);
        log.info("A new order was created in database: {}", orderSaved);

        OrdersCreated eventPayload = new OrdersCreated(
                orderSaved.getId(), new Customer(orderSaved.getKeycloakUserId()), new Card(orderSaved.getCardId()),
                commandPayload.items().stream().map(payloadItem -> new ItemsOrder(
                        payloadItem.productId(), payloadItem.name(), payloadItem.price(), payloadItem.quantityLeft(),
                        payloadItem.requestedQuantity())).toList(), commandPayload.amount(), StatusOrder.PENDING);

        Envelope<OrdersCreated> eventEnvelope = new Envelope<>(
                UUID.randomUUID(), "event.orders.created", MessageType.EVENT,
                Instant.now(), envelope.correlationId(), envelope.messageId(), "orders-source", eventPayload
        );

        log.info("A message to confirm the creation of new order will be published: {}", eventPayload);
        orderEventPublisher.publish(eventEnvelope);


    }

}
