package com.mvv.cards_service.domain.repository;

import com.mvv.cards_service.domain.model.Card;
import com.mvv.cards_service.domain.model.CardType;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepositoryPort {

    Card save(Card card);
    Optional<Card> findById(UUID id);
    Optional<Card> findByUserAndType(UUID keycloakUserId, UUID cardTypeId);
    List<Card> search();


}
