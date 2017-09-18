package tdd.vendingMachine.domain.product;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
public class RackOfShelves {

    private Map<Integer, ShelfWithProducts> shelves;

    public BigDecimal priceOfProductAt(int shelfNumber ){
        ShelfWithProducts shelfWithProductsWithProducts = shelves.get(shelfNumber);
        if(shelfWithProductsWithProducts == null){
            throw new IllegalArgumentException(String.format("Wrong shelf number: '%d'", shelfNumber));
        }

        return shelfWithProductsWithProducts.getPrice().getValue();
    }
}
