package tdd.vendingMachine.domain;

import lombok.extern.slf4j.Slf4j;
import tdd.vendingMachine.domain.product.exception.ProductException;
import tdd.vendingMachine.domain.transaction.PurchaseSession;

@Slf4j
public class VendingMachine {

    private final Display display;

    private final PurchaseSession purchaseSession;

    public VendingMachine(Display display, PurchaseSession purchaseSession) {
        this.display = display;
        this.purchaseSession = purchaseSession;
        display.selectProduct();
    }

    public void selectProductFrom(int shelveNumber){
        try {
            purchaseSession.start(shelveNumber);
        } catch (ProductException e){
            log.info("Exception in resolving product price", e);
            display.noProductFound();
        }
        display.price(purchaseSession.amountLeft());
    }

    //move to session cash management
    public void insertMoney(Money denomination){
       purchaseSession.insert(denomination);
       display.price(purchaseSession.amountLeft());
    }

    //move to session managemment
    public void cancel(){
        purchaseSession.cancel();
        display.selectProduct();
    }




}
