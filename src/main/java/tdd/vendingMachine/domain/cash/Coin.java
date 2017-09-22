package tdd.vendingMachine.domain.cash;

import tdd.vendingMachine.domain.Money;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Coin {
    FIVE(Money.from(5, 0)),
    TWO(Money.from(2, 0)),
    ONE(Money.from(1, 0)),
    HALF(Money.from(0, 50)),
    FIFTH(Money.from(0, 20)),
    TENTH(Money.from(0, 10));

    private static final Map<Money, Coin> validDenominations = validDenominations();

    private final Money value;

    Coin(Money value) {
        this.value = value;
    }

    private static Map<Money, Coin> validDenominations() {
        return Arrays.stream(Coin.values())
                       .collect(Collectors.toMap(
                               denomination -> denomination.value,
                               denomination -> denomination)
                       );
    }

    static boolean isValidDenomination(Money coin){
        return validDenominations.containsKey(coin);
    }

    static Coin valueOf(Money money) {
        return validDenominations().get(money);
    }

    Money getMonetaryValue() {
        return value;
    }




}
