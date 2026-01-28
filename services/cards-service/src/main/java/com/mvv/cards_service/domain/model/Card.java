package com.mvv.cards_service.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Card {


    private final UUID id;
    private final UUID keycloakUserId;
    private final CardType type;
    private BigDecimal balance;

    private Card(UUID id, UUID keycloakUserId, CardType type, BigDecimal balance) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.keycloakUserId = Objects.requireNonNull(keycloakUserId, "keycloak user id must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.balance = Objects.requireNonNull(balance, "balance must not be null");

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("balance must be >= 0");
        }


    }

    public Card(UUID keycloakUserId, CardType type) {

        // Aqui vou validar o keycloakuserID em security
        if (keycloakUserId == null || type == null) {
            throw new RuntimeException();
        }

        this.id = UUID.randomUUID();
        this.keycloakUserId = keycloakUserId;
        this.type = type;
        this.balance = type.getInitialBalance();
    }

    public static Card restore(UUID id, UUID keycloakUserId, CardType type, BigDecimal balance) {

        return new Card(id, keycloakUserId, type, balance);

    }

    public boolean debit(BigDecimal debit) {

        // Separar as exceções
        if (debit == null || debit.compareTo(BigDecimal.valueOf(0)) <= 0 ) {
            throw new IllegalArgumentException("Invalid amount");
        }

        balance = balance.subtract(debit);
        return true;

    }



}
