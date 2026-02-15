package com.mvv.orders_service.application.usecase;

import com.mvv.orders_service.application.payload.command.orders_confirm.OrdersConfirm;
import com.mvv.orders_service.application.payload.common.MessageType;
import com.mvv.orders_service.application.payload.common.StatusOrder;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import com.mvv.orders_service.application.payload.event.orders_confirmed.OrdersConfirmed;
import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.repository.OrderRepositoryPort;
import com.mvv.orders_service.infra.amqp.publisher.OrderEventPublisher;
import com.mvv.orders_service.infra.persistence.adapter.OrderRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfirmOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderRepositoryAdapter orderRepositoryAdapter;
    private final OrderEventPublisher orderEventPublisher;

    public void execute(Envelope<OrdersConfirm> envelope) {

        OrdersConfirm commandPayload = envelope.payload();

        Optional<Order> optionalOrder = orderRepositoryPort.findById(commandPayload.requestId());

        if (optionalOrder.isPresent()) {

            Order order = optionalOrder.get();
            order.confirmOrder();
            orderRepositoryAdapter.save(order);

            log.info("Order was successfully confirmed");

            OrdersConfirmed eventPayload = new OrdersConfirmed(
                    commandPayload.requestId(), commandPayload.customer(), commandPayload.card(), commandPayload.amount(),
                    StatusOrder.CONFIRMED, commandPayload.reservations(), commandPayload.statusPayment(), commandPayload.totalDebited()
                    );

            Envelope<OrdersConfirmed> eventEnvelope = new Envelope<>(
                    UUID.randomUUID(), "event.orders.confirmed", MessageType.EVENT, Instant.now(),
                    order.getId(), envelope.messageId(), "orders-source", eventPayload
            );

            orderEventPublisher.publish(eventEnvelope);

        } else {
            log.warn("Unexpected error. Order id was not found");
        }




    }

}
