package tdd.vendingMachine.domain.transaction;

import lombok.RequiredArgsConstructor;
import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.domain.cash.CashDeposit;
import tdd.vendingMachine.domain.product.RackOfShelves;

@RequiredArgsConstructor
public class PurchaseSession {

    private final RackOfShelves shelves;

    private final CashDeposit cashDeposit;

    private Money totalAmountToPay;

    private Money paid;

    private int shelveNumber;

//    private Status status = Status.EMPTY;

    public void start(int shelveNumber) {
//        shelves.priceOfProductAt(shelveNumber);
    }

    public void insert(Money denomination) {

    }

    public Money amountLeft() {

        return null;
    }

    public void cancel() {


    }

}

