package com.mvv.cards_service.domain.model;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
public class CardType {

    private final UUID id;
    private final String name;
    private final Brand brand;
    private final BigDecimal initialBalance;

    private CardType(UUID id, String name, Brand brand, BigDecimal initialBalance) {

        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.brand = Objects.requireNonNull(brand, "brand must not be null");
        this.initialBalance = Objects.requireNonNull(initialBalance, "initial balance must not be null");

        if (initialBalance.compareTo(BigDecimal.valueOf(50)) < 0) {
            throw new IllegalArgumentException("invalid initial balance");
        }

    }

    public CardType(String name, Brand brand, BigDecimal initialBalance) {

        // TODO - Separete errors
        if ( name == null || name.length() > 80 || brand == null || initialBalance == null ||
                initialBalance.compareTo(BigDecimal.valueOf(50)) < 0 ) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.brand = brand;
        this.initialBalance = initialBalance;


    }

    public static CardType restore(UUID id, String name, Brand brand, BigDecimal initialBalance) {
        return new CardType(id, name, brand, initialBalance);
    }


}
