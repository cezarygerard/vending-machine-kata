package tdd.vendingMachine.domain.product;

import lombok.RequiredArgsConstructor;
import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.domain.product.exception.NoSuchShelfException;
import tdd.vendingMachine.infrastructure.ShelvesRepository;

import java.util.Map;

@RequiredArgsConstructor
public class RackOfShelves {

    private final ShelvesRepository shelves;

    public ShelfWithProducts findNonEmptyShelf(int shelfNumber){
        ShelfWithProducts shelfWithProducts = shelves.get(shelfNumber);
        if(emptyShelf(shelfWithProducts)){
            throw new NoSuchShelfException(String.format("Wrong shelf number: '%d'", shelfNumber));
        }

        return shelfWithProducts;
    }

    private boolean emptyShelf(ShelfWithProducts shelfWithProducts) {
        return shelfWithProducts == null || shelfWithProducts.getNumberOfProducts() == 0;
    }


}
