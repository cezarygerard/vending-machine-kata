package tdd.vendingMachine.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tdd.vendingMachine.domain.Money;
import tdd.vendingMachine.domain.product.exception.EmptyShelfException;

import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor
@Getter(PACKAGE)
public class ShelfWithProducts {

    private ProductType productType;

    private int numberOfProducts;

    private ProductReleaseRobot robot;

    public Money getPrice() {

        if(numberOfProducts <= 0 ){
           throw new EmptyShelfException("Cannot retrive price form empty shelf");
        }

        return productType.getPrice();
    }

    public void dispense() {
        robot.releaseProductFrom(this);
    }
}
