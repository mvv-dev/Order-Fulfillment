package com.mvv.orders_service.application.usecase;

import com.mvv.orders_service.application.payload.command.orders_cancel.OrdersCancel;
import com.mvv.orders_service.application.payload.common.MessageType;
import com.mvv.orders_service.application.payload.common.ReservationError;
import com.mvv.orders_service.application.payload.common.StatusOrder;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import com.mvv.orders_service.application.payload.event.orders_cancelled.OrdersCancelled;
import com.mvv.orders_service.application.port.out.OrdersEventPublisherPort;
import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.repository.OrderRepositoryPort;
import com.mvv.orders_service.infra.persistence.adapter.OrderRepositoryAdapter;
import com.mvv.orders_service.infra.persistence.entity.OrderEntity;
import com.mvv.orders_service.infra.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CancelOrderUseCase {

    private final OrderRepositoryAdapter orderRepositoryAdapter;
    private final OrderRepositoryPort orderRepositoryPort;
    private final OrdersEventPublisherPort ordersEventPublisherPort;
    private final OrderJpaRepository orderJpaRepository;

    public void execute(Envelope<OrdersCancel> envelope) {

        OrdersCancel commandPayload = envelope.payload();
        List<OrderEntity> orders = orderJpaRepository.findAll();
        System.out.println("Recebi a solicitação pra cancelar, esses são os ids dos pedidos no banco: ");
        for (var order : orders) {
            System.out.println(order.getId());
        }
        Optional<Order> orderOptional = orderRepositoryPort.findById(commandPayload.requestId());


        if (orderOptional.isPresent()) {

            Order orderToCancel = orderOptional.get();
            orderToCancel.cancelOrder();
            orderRepositoryAdapter.save(orderToCancel);

            OrdersCancelled eventPayload = new OrdersCancelled(
                    commandPayload.requestId(), commandPayload.reason(), commandPayload.errors().stream().
                    map(commandError -> new ReservationError(commandError.item(), commandError.code(),
                            commandError.message(), commandError.available())).toList(), StatusOrder.CANCELLED);

            Envelope<OrdersCancelled> eventEnvelope = new Envelope<>(
                    UUID.randomUUID(), "event.oders.cancelled", MessageType.EVENT, Instant.now(),
                    envelope.correlationId(), envelope.messageId(), "orders-source", eventPayload
            );

            ordersEventPublisherPort.publish(eventEnvelope);



        } else {
            System.out.println("id: " + commandPayload.requestId());
            System.out.println("Error, invalid order");
        }

    }

}
