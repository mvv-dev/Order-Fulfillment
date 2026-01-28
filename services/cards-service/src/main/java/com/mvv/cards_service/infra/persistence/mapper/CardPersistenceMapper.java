package com.mvv.cards_service.infra.persistence.mapper;

import com.mvv.cards_service.domain.model.Card;
import com.mvv.cards_service.domain.model.CardType;
import com.mvv.cards_service.infra.persistence.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardPersistenceMapper {

    private final CardTypePersistenceMapper cardTypePersistenceMapper;

    public CardEntity toEntity(Card card) {
        CardEntity entity = new CardEntity();
        entity.setId(card.getId());
        entity.setKeycloakUserId(card.getKeycloakUserId());
        entity.setBalance(card.getBalance());
        return entity;
    }

    public Card toDomain(CardEntity entity) {
        CardType cardType = cardTypePersistenceMapper.toDomain(entity.getType());
        return Card.restore(
                entity.getId(), entity.getKeycloakUserId(), cardType, entity.getBalance()
        );
    }

}
