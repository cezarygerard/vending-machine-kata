package tdd.vendingMachine.domain;

interface Display {

    void price(Money money);

    void noProductFound();

    void selectProduct();

    char cannotRefundChange();
}


