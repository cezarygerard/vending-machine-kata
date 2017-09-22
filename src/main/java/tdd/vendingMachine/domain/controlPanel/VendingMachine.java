package tdd.vendingMachine.domain.controlPanel;

import lombok.extern.slf4j.Slf4j;
import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.domain.cash.RefundException;
import tdd.vendingMachine.domain.product.exception.ProductException;
import tdd.vendingMachine.domain.transaction.PurchaseProcess;

@Slf4j
public class VendingMachine {

    private final Display display;

    private final PurchaseProcess purchaseSession;

    public VendingMachine(Display display, PurchaseProcess purchaseSession) {
        this.display = display;
        this.purchaseSession = purchaseSession;
        display.selectProduct();
    }

    public void selectProductFrom(int shelveNumber) {
        try {
            purchaseSession.selectProduct(shelveNumber);
        } catch (ProductException e) {
            log.info("Exception in resolving product", e);
            display.noProductFound();
        }
        display.price(purchaseSession.amountLeftToPay());
    }

    public void insertMoney(Money denomination) {
        try {
            purchaseSession.insert(denomination);
            display.price(purchaseSession.amountLeftToPay());
        } catch (RefundException e) {
            log.info("Exception in refund", e);
            display.cannotRefundChange();
        }
    }

    public void cancel() {
        purchaseSession.cancel();
        display.selectProduct();
    }


}
