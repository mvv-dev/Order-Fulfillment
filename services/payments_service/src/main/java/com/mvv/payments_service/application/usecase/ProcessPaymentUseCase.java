package com.mvv.payments_service.application.usecase;

import com.mvv.payments_service.application.payload.command.CardsDebit;
import com.mvv.payments_service.application.payload.command.PaymentsProcess;
import com.mvv.payments_service.application.payload.common.MessageType;
import com.mvv.payments_service.application.payload.common.envelope.Envelope;
import com.mvv.payments_service.application.port.out.PaymentsEventsPublisherPort;
import com.mvv.payments_service.domain.model.Payment;
import com.mvv.payments_service.infra.persistence.adapter.PaymentRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProcessPaymentUseCase {

    private final PaymentRepositoryAdapter paymentRepositoryAdapter;
    private final PaymentsEventsPublisherPort publisherPort;

    public void execute(Envelope<PaymentsProcess> envelope) {

        // 1 - save payments with pending status and publish a command to card

        PaymentsProcess payload = envelope.payload();
        Payment paymentToSave = new Payment(payload.requestId(), payload.customer().keycloakUserId(),
                payload.card().cardId(), payload.amount());
        Payment paymentSaved = paymentRepositoryAdapter.save(paymentToSave);
        System.out.println("Processo de pagamento registrado no banco");

        CardsDebit cardsDebit = new CardsDebit(payload.requestId(), payload.customer(),
                payload.card(), payload.amount(), payload.statusOrder(), payload.reservations(), paymentSaved.getId());

        Envelope<CardsDebit> commandPayload = new Envelope<>(
                UUID.randomUUID(), "command.cards.debit", MessageType.COMMAND, Instant.now(),
                envelope.correlationId(), envelope.messageId(), "payments-source", cardsDebit
        );

        System.out.println("Mensagem será enviada para cards");
        publisherPort.publish(commandPayload);





        //




    }

}
