package com.mvv.cards_service.application.port.out;

import com.mvv.cards_service.application.payload.common.envelope.Envelope;

public interface CardsEventPublisherPort {
    void publish(Envelope<?> envelope);
}
