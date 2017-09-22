package tdd.vendingMachine.infrastructure;

import tdd.vendingMachine.domain.cash.Coin;

public interface MoneyStorageMachine {
    void releaseCoins(Iterable<Coin> coinsToRelease);
}
