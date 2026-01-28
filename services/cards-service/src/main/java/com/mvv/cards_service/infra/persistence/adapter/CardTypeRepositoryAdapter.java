package com.mvv.cards_service.infra.persistence.adapter;

import com.mvv.cards_service.domain.model.CardType;
import com.mvv.cards_service.domain.repository.CardTypeRepositoryPort;
import com.mvv.cards_service.infra.persistence.entity.CardTypeEntity;
import com.mvv.cards_service.infra.persistence.mapper.CardTypePersistenceMapper;
import com.mvv.cards_service.infra.persistence.repository.CardTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CardTypeRepositoryAdapter implements CardTypeRepositoryPort {

    private final CardTypePersistenceMapper cardTypeMapper;
    private final CardTypeJpaRepository cardTypeJpaRepository;

    @Override
    public CardType save(CardType cardType) {
        CardTypeEntity cardTypeEntity = cardTypeMapper.toEntity(cardType);
        CardTypeEntity cardTypeSaved = cardTypeJpaRepository.save(cardTypeEntity);
        return cardTypeMapper.toDomain(cardTypeSaved);
    }

    @Override
    public Optional<CardType> findById(UUID id) {
        return cardTypeJpaRepository.findById(id).map(cardTypeMapper::toDomain);
    }

    @Override
    public List<CardType> search() {
        return cardTypeJpaRepository.findAll().stream()
                .map(cardTypeMapper::toDomain).toList();
    }

    @Override
    public Optional<CardType> findByName(String name) {
        return cardTypeJpaRepository.findByName(name).map(cardTypeMapper::toDomain);
    }
}
