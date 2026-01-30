package com.mvv.cards_service.application.usecase;

import com.mvv.cards_service.application.exception.DuplicateRegisterException;
import com.mvv.cards_service.application.usecase.command.IssueCardCommand;
import com.mvv.cards_service.domain.model.Card;
import com.mvv.cards_service.domain.repository.CardRepositoryPort;
import com.mvv.cards_service.domain.repository.CardTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssueCardUseCase {

    private final CardRepositoryPort cardRepositoryPort;
    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    public Card execute(IssueCardCommand command) {

        // First check if this cardType is valid
        var cardType = cardTypeRepositoryPort.findById(command.cardTypeId());
        if (cardType.isEmpty()) throw new IllegalArgumentException("This cardType does not exist");

        // Check if this user already have this card
        var cardFoundByIdAndUser = cardRepositoryPort.findByUserAndType(UUID.fromString(command.customerSub()), command.cardTypeId());
        if (cardFoundByIdAndUser.isPresent()) throw new DuplicateRegisterException("This user already have this card");

        Card card = new Card(UUID.fromString(command.customerSub()), cardType.get(), cardType.get().getInitialBalance());
        cardRepositoryPort.save(card);
        return card;

    }

}
