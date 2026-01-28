package com.mvv.cards_service.infra.persistence.repository;

import com.mvv.cards_service.infra.persistence.entity.CardTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardTypeJpaRepository extends JpaRepository<CardTypeEntity, UUID> {
    Optional<CardTypeEntity> findByName(String name);
}
