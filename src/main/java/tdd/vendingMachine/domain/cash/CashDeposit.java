package tdd.vendingMachine.domain.cash;

import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.infrastructure.MoneyStorageMachine;

import java.util.*;

public class CashDeposit {

    final SortedMap<Coin, Integer> storedCoins;

    private final MoneyStorageMachine moneyStorageMachine;

    public CashDeposit(MoneyStorageMachine moneyStorageMachine) {
        this.moneyStorageMachine = moneyStorageMachine;

        Comparator<Coin> highToLow = (c1, c2) ->
                c2.getMonetaryValue()
                        .compareTo(c1.getMonetaryValue());

        this.storedCoins = new TreeMap<>(highToLow);

        Arrays.stream(Coin.values())
                .forEach(denomination -> storedCoins.put(denomination, 0));
    }

    public boolean store(Money coin) {
        if (Coin.isValidDenomination(coin)) {
            addToStorage(Coin.valueOf(coin));
            return true;
        }
        return false;
    }

    private void addToStorage(Coin coin) {
        Integer value = storedCoins.get(coin);
        storedCoins.put(coin, value + 1);
    }

    public void refund(Money amountToRefund) {
        List<Coin> coinsToRelease = calculateCoinsToRelease(amountToRefund);
        if (cannotRefund(amountToRefund, coinsToRelease)) {
            throw new RefundException();
        }
        moneyStorageMachine.releaseCoins(coinsToRelease);
        removeFromStorage(coinsToRelease);
    }

    private boolean cannotRefund(Money amountToRefund, List<Coin> coinsToRelease) {
        return amountToRefund.compareTo(Money.ZERO) > 0
                && coinsToRelease.size() == 0;
    }

    private void removeFromStorage(List<Coin> coinsToRelease) {
        coinsToRelease.stream()
                .forEach(coin -> {
                            Integer value = storedCoins.get(coin);
                            storedCoins.put(coin, value - 1);
                        }
                );
    }

    private List<Coin> calculateCoinsToRelease(Money amountToRefund) {
        List<Coin> coinsToRelease = new ArrayList<>();

        Iterator<Map.Entry<Coin, Integer>> iterator = this.storedCoins.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Coin, Integer> entry = iterator.next();
            int numberOfCoins = entry.getValue();
            while (numberOfCoins > 0) {
                Money amount = amountToRefund.subtract(entry.getKey().getMonetaryValue());
                if (amount.compareTo(Money.ZERO) >= 0) {
                    amountToRefund = amount;
                    numberOfCoins--;
                    coinsToRelease.add(entry.getKey());
                }
                if (amount.compareTo(Money.ZERO) == 0) {
                    return coinsToRelease;
                }

                if (amount.compareTo(Money.ZERO) < 0) {
                    break;
                }

            }
        }

        return coinsToRelease;
    }

}
