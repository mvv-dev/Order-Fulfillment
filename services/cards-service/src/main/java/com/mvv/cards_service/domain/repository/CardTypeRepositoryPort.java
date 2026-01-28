package com.mvv.cards_service.domain.repository;

import com.mvv.cards_service.domain.model.CardType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardTypeRepositoryPort {

    CardType save(CardType cardType);
    Optional<CardType> findById(UUID id);
    List<CardType> search();
    Optional<CardType> findByName(String name);

}
