package com.mvv.saga_service.application.port.out;

import com.mvv.saga_service.application.contracts.common.Envelope;

public interface CommandPublisherPort {

    void publish(Envelope<?> envelope);

}
