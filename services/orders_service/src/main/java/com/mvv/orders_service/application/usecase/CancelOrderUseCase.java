package com.mvv.orders_service.application.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.orders_service.application.payload.command.orders_cancel.OrdersCancel;
import com.mvv.orders_service.application.payload.common.MessageType;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import com.mvv.orders_service.application.payload.event.orders_cancelled.OrdersCancelled;
import com.mvv.orders_service.application.port.out.OrdersEventPublisherPort;
import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.repository.OrderRepositoryPort;
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
public class CancelOrderUseCase {

    private final OrdersEventPublisherPort ordersEventPublisherPort;
    private final OrderRepositoryAdapter orderRepositoryAdapter;
    private final OrderRepositoryPort orderRepositoryPort;
    private final ObjectMapper objectMapper;

    public void execute(Envelope<OrdersCancel> envelope) {

        OrdersCancel commandPayload = envelope.payload();

        Optional<Order> orderOptional = orderRepositoryPort.findById(commandPayload.requestId());

        if (orderOptional.isPresent()) {

            Order order = orderOptional.get();
            order.cancelOrder();
            orderRepositoryAdapter.save(order);
            log.info("Order was successfully cancelled.");

            OrdersCancelled eventPayload = new OrdersCancelled(
                    order.getId(), commandPayload.customer(), commandPayload.card(), commandPayload.amount(),
                    commandPayload.statusOrder(), commandPayload.reservations(), commandPayload.statusPayment(),
                    commandPayload.totalDebited()
            );

            Envelope<OrdersCancelled> eventEnvelope = new Envelope<>(
                    UUID.randomUUID(), "event.orders.cancelled", MessageType.EVENT, Instant.now(),
                    order.getId(), envelope.messageId(), "orders-source", eventPayload
            );

            ordersEventPublisherPort.publish(eventEnvelope);

        } else {
            log.warn("Unexpected error. Order id was not found");
        }


    }


}
