package tdd.vendingMachine.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tdd.vendingMachine.domain.product.exception.EmptyShelfException;

import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor
@Getter(PACKAGE)
class ShelfWithProducts {

    private ProductType productType;

    private int numberOfProducts;

    public Price getPrice() {

        if(numberOfProducts <= 0 ){
           throw new EmptyShelfException("Cannot retrive price form empty shelf");
        }

        return productType.getPrice();
    }
}
