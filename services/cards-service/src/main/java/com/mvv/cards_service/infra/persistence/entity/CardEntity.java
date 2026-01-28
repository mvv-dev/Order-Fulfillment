package com.mvv.cards_service.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "card")
@Setter
@Getter
public class CardEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private CardTypeEntity type;

    @Column(name = "keycloak_user_id")
    private UUID keycloakUserId;

    private BigDecimal balance;

}
