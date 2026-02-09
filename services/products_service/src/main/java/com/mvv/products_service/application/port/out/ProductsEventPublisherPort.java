package com.mvv.products_service.application.port.out;

import com.mvv.products_service.application.payload.common.envelope.Envelope;

public interface ProductsEventPublisherPort {

    void publish(Envelope<?> envelope);

}
