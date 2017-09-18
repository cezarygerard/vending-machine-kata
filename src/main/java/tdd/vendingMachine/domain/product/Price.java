package tdd.vendingMachine.domain.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

import static lombok.AccessLevel.PACKAGE;

@EqualsAndHashCode
class Price {

    @Getter(PACKAGE)
    private final BigDecimal value;

    private Price(BigDecimal value) {
        this.value = value;
    }

    static Price of(BigDecimal value) {
        return new Price(value);
    }
}
