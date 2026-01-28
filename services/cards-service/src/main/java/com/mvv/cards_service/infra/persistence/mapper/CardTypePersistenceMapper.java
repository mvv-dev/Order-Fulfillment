package com.mvv.cards_service.infra.persistence.mapper;

import com.mvv.cards_service.domain.model.CardType;
import com.mvv.cards_service.infra.persistence.entity.CardEntity;
import com.mvv.cards_service.infra.persistence.entity.CardTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class CardTypePersistenceMapper {

    public CardTypeEntity toEntity(CardType cardType) {
        CardTypeEntity cardTypeEntity = new CardTypeEntity();
        cardTypeEntity.setId(cardType.getId());
        cardTypeEntity.setName(cardType.getName());
        cardTypeEntity.setBrand(cardType.getBrand());
        cardTypeEntity.setInitialBalance(cardType.getInitialBalance());
        return cardTypeEntity;
    }

    public CardType toDomain(CardTypeEntity cardTypeEntity) {
        return CardType.restore(
                cardTypeEntity.getId(), cardTypeEntity.getName(),
                cardTypeEntity.getBrand(), cardTypeEntity.getInitialBalance()
        );
    }

}
