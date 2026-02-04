package com.mvv.saga_service.application;

import com.mvv.saga_service.contratcts.events.OrderSolicited;
import com.mvv.saga_service.infra.amqp.publisher.SagaCommandsPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SagaEventHandler {

    private final SagaEventCommandMapper mapper;
    private final SagaCommandsPublisher publisher;

    public void onOrderSolicited(OrderSolicited orderSolicited) {

        var cmd = mapper.productsCheckItemsCommand(orderSolicited);
        publisher.publishProductsCheckItems(cmd);

    }

}
