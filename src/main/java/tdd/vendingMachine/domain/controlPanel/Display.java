package tdd.vendingMachine.domain.controlPanel;

import tdd.vendingMachine.domain.Money;

public interface Display {

    void price(Money money);

    void noProductFound();

    void selectProduct();

    char cannotRefundChange();
}


