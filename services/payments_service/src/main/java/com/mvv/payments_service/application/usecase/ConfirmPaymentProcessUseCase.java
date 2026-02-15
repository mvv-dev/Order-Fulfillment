package com.mvv.payments_service.application.usecase;

import com.mvv.payments_service.application.payload.common.MessageType;
import com.mvv.payments_service.application.payload.common.StatusPayment;
import com.mvv.payments_service.application.payload.common.envelope.Envelope;
import com.mvv.payments_service.application.payload.event.CardDebitProcessed;
import com.mvv.payments_service.application.payload.event.PaymentProcessed;
import com.mvv.payments_service.application.port.out.PaymentsEventsPublisherPort;
import com.mvv.payments_service.domain.model.Payment;
import com.mvv.payments_service.domain.repository.PaymentRepositoryPort;
import com.mvv.payments_service.infra.persistence.adapter.PaymentRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ConfirmPaymentProcessUseCase {

    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentsEventsPublisherPort eventsPublisherPort;

    public void execute(Envelope<CardDebitProcessed> envelope) {

        CardDebitProcessed eventPayload = envelope.payload();

        Optional<Payment> paymentOptional = paymentRepositoryPort.findById(eventPayload.paymentId());

        if (paymentOptional.isPresent()) {

            Payment payment = paymentOptional.get();

            if (eventPayload.statusPayment().equals(StatusPayment.FAILED)) {
                payment.update(com.mvv.payments_service.domain.model.StatusPayment.DENIED);
                paymentRepositoryPort.save(payment);
            } else {
                payment.update(com.mvv.payments_service.domain.model.StatusPayment.APPROVED);
                paymentRepositoryPort.save(payment);
            }

        } else {

            System.out.println("Unexpected error: paymentId was not found");
            throw new RuntimeException("Payment not found");
        }

        PaymentProcessed paymentProcessed = new PaymentProcessed(
                eventPayload.requestId(), eventPayload.customer(), eventPayload.card(), eventPayload.amount(),
                eventPayload.statusOrder(), eventPayload.reservations(), eventPayload.statusPayment(), eventPayload.totalDebited(),
                eventPayload.errors()
        );

        Envelope<PaymentProcessed> eventEnvelope = new Envelope<>(
                UUID.randomUUID(), "event.payments.processed", MessageType.EVENT, Instant.now(),
                envelope.correlationId(), envelope.messageId(), "payments-source", paymentProcessed

        );

        System.out.println("Processamento e atualização de pagamento concluída");
        System.out.println("Mensagem enviada para saga");
        eventsPublisherPort.publish(eventEnvelope);


    }

}
