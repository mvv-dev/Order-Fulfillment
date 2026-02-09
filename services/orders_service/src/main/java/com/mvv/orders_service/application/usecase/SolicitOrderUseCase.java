package com.mvv.orders_service.application.usecase;


import com.mvv.orders_service.application.payload.common.Card;
import com.mvv.orders_service.application.payload.common.Customer;
import com.mvv.orders_service.application.payload.common.MessageType;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import com.mvv.orders_service.application.payload.event.orders_solicited.ItemsSolicited;
import com.mvv.orders_service.application.payload.event.orders_solicited.OrdersSolicited;
import com.mvv.orders_service.application.port.out.OrdersEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SolicitOrderUseCase {

    private final OrdersEventPublisherPort publisherPort;

    public void execute(UUID customerId, UUID cardId, List<ItemsSolicited> items) {

        UUID requestId = UUID.randomUUID();

        OrdersSolicited payload = new OrdersSolicited(
                requestId, new Customer(customerId), new Card(cardId), items
        );

        Envelope<OrdersSolicited> eventEnvelope = new Envelope<>(
                UUID.randomUUID(),
                "event.orders.solicited", MessageType.EVENT,
                Instant.now(), payload.requestId(), null, "orders-source",
                payload
        );

        publisherPort.publish(eventEnvelope);

    }

}
