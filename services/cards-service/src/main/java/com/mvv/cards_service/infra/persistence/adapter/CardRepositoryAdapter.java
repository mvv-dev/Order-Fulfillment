package com.mvv.cards_service.infra.persistence.adapter;

import com.mvv.cards_service.domain.model.Card;
import com.mvv.cards_service.domain.repository.CardRepositoryPort;
import com.mvv.cards_service.infra.persistence.entity.CardEntity;
import com.mvv.cards_service.infra.persistence.mapper.CardPersistenceMapper;
import com.mvv.cards_service.infra.persistence.repository.CardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final CardJpaRepository cardJpaRepository;
    private final CardPersistenceMapper cardPersistenceMapper;

    @Override
    public Card save(Card card) {

        CardEntity cardEntity = cardPersistenceMapper.toEntity(card);
        CardEntity saved = cardJpaRepository.save(cardEntity);
        return cardPersistenceMapper.toDomain(saved);

    }

    @Override
    public Optional<Card> findById(UUID id) {

        var cardFound = cardJpaRepository.findById(id);
        return cardFound.map(cardPersistenceMapper::toDomain);

    }

    @Override
    public Optional<Card> findByUserAndType(UUID keycloakUserId, UUID cardTypeId) {

        var cardFound = cardJpaRepository.findByUserAndType(keycloakUserId, cardTypeId);
        return cardFound.map(cardPersistenceMapper::toDomain);

    }

    @Override
    public List<Card> search() {
        return cardJpaRepository.findAll().stream().
                map(cardPersistenceMapper::toDomain).toList();

    }
}
