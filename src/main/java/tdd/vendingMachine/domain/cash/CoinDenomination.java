package tdd.vendingMachine.domain.cash;

import tdd.vendingMachine.domain.Money;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

enum CoinDenomination {
    FIVE(Money.from(5, 0)),
    TWO(Money.from(2, 0)),
    ONE(Money.from(1, 0)),
    HALF(Money.from(0, 50)),
    FIFTH(Money.from(0, 20)),
    TENTH(Money.from(0, 10));

    CoinDenomination(Money value) {
        this.value = value;
    }

    private final Money value;

    private static final Map<Money, CoinDenomination> validDenominations = validDenominations();

    private static Map<Money, CoinDenomination> validDenominations() {
        return Arrays.stream(CoinDenomination.values())
                       .collect(Collectors.toMap(
                               denomination -> denomination.value,
                               denomination -> denomination)
                       );
    }

    static boolean isValidDenomination(Money coin){
        return validDenominations.containsKey(coin);
    }

    static CoinDenomination valueOf(Money money){
        return validDenominations().get(money);
    }
}
