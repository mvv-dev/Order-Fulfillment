package com.mvv.payments_service.application.port.out;

import com.mvv.payments_service.application.payload.common.envelope.Envelope;

public interface PaymentsEventsPublisherPort {

    void publish(Envelope<?> envelope);

}
