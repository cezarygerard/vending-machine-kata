package tdd.vendingMachine.domain.product;

import lombok.RequiredArgsConstructor;
import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.domain.product.exception.NoSuchShelfException;

import java.util.Map;

@RequiredArgsConstructor
public class RackOfShelves {

    //repository
    private final Map<Integer, ShelfWithProducts> shelves;

    public Money priceOfProductAt(int shelfNumber ){
        return findShelf(shelfNumber).getPrice();
    }

//    public void releaseProduct(int shelfNumber){
//        return findShelf(shelfNumber).releaseProduct();
//    }

    private ShelfWithProducts findShelf(int shelfNumber){
        ShelfWithProducts shelfWithProductsWithProducts = shelves.get(shelfNumber);
        if(shelfWithProductsWithProducts == null){
            throw new NoSuchShelfException(String.format("Wrong shelf number: '%d'", shelfNumber));
        }

        return shelfWithProductsWithProducts;
    }


}
