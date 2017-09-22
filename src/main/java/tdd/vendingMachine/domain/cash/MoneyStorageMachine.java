package tdd.vendingMachine.domain.cash;

public interface MoneyStorageMachine {
    void releaseCoins(Iterable<Coin> coinsToRelease);
}
