package tdd.vendingMachine.infrastructure;

import tdd.vendingMachine.domain.product.ShelfWithProducts;

public interface ProductReleaseRobot {

    void releaseProductFrom(ShelfWithProducts shelf);

}
