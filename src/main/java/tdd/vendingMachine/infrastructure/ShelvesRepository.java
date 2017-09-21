package tdd.vendingMachine.infrastructure;

import tdd.vendingMachine.domain.product.ShelfWithProducts;

public interface ShelvesRepository {
    ShelfWithProducts get(int shelfNumber);
}
