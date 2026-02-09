package com.mvv.orders_service.application.port.out;

import com.mvv.orders_service.application.payload.common.envelope.Envelope;

public interface OrdersEventPublisherPort {

    void publish(Envelope<?> envelope);

}
