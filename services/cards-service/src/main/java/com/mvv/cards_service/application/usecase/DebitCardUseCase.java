package com.mvv.cards_service.application.usecase;

import com.mvv.cards_service.application.payload.command.CardsDebit;
import com.mvv.cards_service.application.payload.common.ErrorCard;
import com.mvv.cards_service.application.payload.common.MessageType;
import com.mvv.cards_service.application.payload.common.StatusPayment;
import com.mvv.cards_service.application.payload.common.envelope.Envelope;
import com.mvv.cards_service.application.payload.event.CardDebitProcessed;
import com.mvv.cards_service.application.port.out.CardsEventPublisherPort;
import com.mvv.cards_service.domain.model.Card;
import com.mvv.cards_service.infra.persistence.adapter.CardRepositoryAdapter;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DebitCardUseCase {

    private final CardRepositoryAdapter cardRepositoryAdapter;
    private final CardsEventPublisherPort publisherPort;

    @Transactional
    public void execute(Envelope<CardsDebit> envelope) {

        System.out.println("iniciando validação do cartão e saldo");

        CardsDebit commandPayload = envelope.payload();
        List<ErrorCard> errorCards = new ArrayList<>();
        BigDecimal debitedAmount = BigDecimal.ZERO;

        Optional<Card> cardOptional = cardRepositoryAdapter.findById(commandPayload.card().cardId());

        if (cardOptional.isPresent()) {

            Card card = cardOptional.get();

            if (!card.getKeycloakUserId().equals(commandPayload.customer().keycloakUserId())) {
                errorCards.add(new ErrorCard("NOT_FOUND", "This user does not have this card"));
            } else {
                BigDecimal cardBalance = card.getBalance();
                if (cardBalance.compareTo(commandPayload.amount()) < 0) {
                    errorCards.add(new ErrorCard("INSUFFICIENT_BALANCE",
                            "This card does not have enough balance to make this order"));
                } else {
                    card.debit(commandPayload.amount());
                    debitedAmount = debitedAmount.add(commandPayload.amount());
                    cardRepositoryAdapter.save(card);
                    System.out.println("Saldo debitado com sucesso");
                }
            }



        } else {
            errorCards.add(new ErrorCard("NOT_FOUND", "This user does not have this card"));
        }

        StatusPayment statusPayment = errorCards.isEmpty() ? StatusPayment.OK : StatusPayment.FAILED;

        CardDebitProcessed eventPayload = new CardDebitProcessed(
                commandPayload.requestId(), commandPayload.customer(), commandPayload.card(), commandPayload.amount(),
                commandPayload.statusOrder(), commandPayload.reservations(), commandPayload.paymentId(),statusPayment, debitedAmount
                ,errorCards
        );

        Envelope<CardDebitProcessed> eventEnvelope = new Envelope<>(
                UUID.randomUUID(), "event.cards.debit.processed", MessageType.EVENT, Instant.now(),
                envelope.correlationId(), envelope.messageId(), "cards-source", eventPayload
        );

        publisherPort.publish(eventEnvelope);


    }

}
