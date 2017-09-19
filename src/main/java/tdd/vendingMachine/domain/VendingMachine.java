package tdd.vendingMachine.domain;

import lombok.RequiredArgsConstructor;
import tdd.vendingMachine.domain.product.RackOfShelves;
import tdd.vendingMachine.domain.product.exception.ProductException;
import tdd.vendingMachine.domain.transaction.PurchaseSessionFactory;

@RequiredArgsConstructor
public class VendingMachine {

    static final String NO_PRODUCT = "No product found";

    private final RackOfShelves shelves;

    private final Display display;

    private final PurchaseSessionFactory purchaseSessionFactory;

    public void selectProductFrom(int shelveNumber){
        try {
            Money price = shelves.priceOfProductAt(shelveNumber);
            display.showText(price.toString());
        } catch (ProductException p){
            display.showText(NO_PRODUCT);
            return;
        }

//        purchaseSessionFactory.newSession()
    }

    public void insertMoney(){

    }

    public void cancel(){

    }




}
