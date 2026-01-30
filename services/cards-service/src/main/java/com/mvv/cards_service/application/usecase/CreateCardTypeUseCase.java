package com.mvv.cards_service.application.usecase;

import com.mvv.cards_service.application.exception.DuplicateRegisterException;
import com.mvv.cards_service.application.usecase.command.CreateCardTypeCommand;
import com.mvv.cards_service.domain.model.Brand;
import com.mvv.cards_service.domain.model.CardType;
import com.mvv.cards_service.domain.repository.CardTypeRepositoryPort;
import com.mvv.cards_service.infra.persistence.entity.CardTypeEntity;
import com.mvv.cards_service.infra.persistence.mapper.CardTypePersistenceMapper;
import com.mvv.cards_service.infra.persistence.repository.CardTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateCardTypeUseCase {

    private final CardTypeRepositoryPort cardTypeRepository;

    public CardType execute(CreateCardTypeCommand cmd) {

        var cardFoudByThisName = cardTypeRepository.findByName(cmd.name());
        if (cardFoudByThisName.isPresent()) {
            throw new DuplicateRegisterException("Card Type already exists");
        }

        // Constructors' going to validate this type
        CardType type = new CardType(cmd.name(), cmd.brand(), cmd.initialBalance());
        return cardTypeRepository.save(type);


    }


}
