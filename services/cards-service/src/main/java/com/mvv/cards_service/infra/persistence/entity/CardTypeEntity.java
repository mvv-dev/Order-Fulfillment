package com.mvv.cards_service.infra.persistence.entity;

import com.mvv.cards_service.domain.model.Brand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "card_type")
@Getter
@Setter
public class CardTypeEntity {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Column(name = "initial_balance")
    private BigDecimal initialBalance;

}
