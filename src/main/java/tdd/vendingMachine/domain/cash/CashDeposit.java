package tdd.vendingMachine.domain.cash;

import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.infrastructure.MoneyStorage;

import java.util.*;

public class CashDeposit {

    private final Map<CoinDenomination, Integer> storageState;

    private final MoneyStorage moneyStorage;

    public CashDeposit(MoneyStorage moneyStorage) {
        this.moneyStorage = moneyStorage;
        this.storageState = new HashMap<>(CoinDenomination.values().length, 1.0f);
        Arrays.stream(CoinDenomination.values())
                .forEach( denomination -> storageState.put(denomination,0));
    }

    public boolean store(Money coin) {
        if(CoinDenomination.isValidDenomination(coin)){
            add(CoinDenomination.valueOf(coin));
            return true;
        }
        return false;
    }

    private void add(CoinDenomination coin) {

    }

    public void refund(Money amountToRefund) {

    }
}
