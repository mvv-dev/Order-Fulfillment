package com.mvv.cards_service.infra.persistence.repository;

import com.mvv.cards_service.infra.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CardJpaRepository extends JpaRepository<CardEntity, UUID> {

    @Query("select c from CardEntity c where c.keycloakUserId = ?1 and c.type.id = ?2")
    Optional<CardEntity> findByUserAndType(UUID userId, UUID typeId);

}
