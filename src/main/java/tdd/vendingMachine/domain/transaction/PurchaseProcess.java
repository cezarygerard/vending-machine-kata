package tdd.vendingMachine.domain.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.domain.cash.CashDeposit;
import tdd.vendingMachine.domain.cash.InvalidCoinException;
import tdd.vendingMachine.domain.cash.RefundException;
import tdd.vendingMachine.domain.product.RackOfShelves;
import tdd.vendingMachine.domain.product.ShelfWithProducts;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class PurchaseProcess {

    private final RackOfShelves shelves;

    private final CashDeposit cashDeposit;

    private Money paid = Money.ZERO;

    private ShelfWithProducts selectedShelf;

    public void selectProduct(int shelveNumber) {
        selectedShelf = shelves.findNonEmptyShelf(shelveNumber);
        finishTransactionIfPossible();
    }

    public void insert(Money coin) {
        try {
            cashDeposit.store(coin);
            paid = paid.add(coin);
        } catch (InvalidCoinException e){
            log.warn("Invalid coin inserted: {}", coin, e);
        }
        finishTransactionIfPossible();
    }

    public Money amountLeftToPay() {
        Money price = Optional.ofNullable(selectedShelf)
                .map(ShelfWithProducts::getPrice)
                .orElse(Money.ZERO);

        return  price.subtract(paid);
    }

    public Money amountPaid() {
        return paid;
    }

    public void cancel() {
        cashDeposit.refund(paid);
        selectedShelf = null;
        paid = Money.ZERO;
    }

    private void finishTransactionIfPossible() {
        if(paidTotalAmount()){
            selectedShelf.dispense();
        }

        if(excessPayment() && productIsSelected()){
            finishWithChangeRefund();
        }
    }

    private boolean productIsSelected() {
        return selectedShelf != null;
    }

    private boolean excessPayment() {
        return amountLeftToPay().compareTo(Money.ZERO) <0;
    }

    private boolean paidTotalAmount() {
        return amountLeftToPay().equals(Money.ZERO);
    }

    private void finishWithChangeRefund() {
        Money change = paid.subtract(selectedShelf.getPrice());
        try {
            cashDeposit.refund(change);
            selectedShelf.dispense();
        } catch (RefundException refundException) {
            log.warn("Cannot dispense change: {}, cancelling transaction", change);
            cancel();
            throw refundException;
        }
    }



}

