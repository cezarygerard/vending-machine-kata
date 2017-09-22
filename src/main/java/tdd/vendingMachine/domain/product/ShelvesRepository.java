package tdd.vendingMachine.domain.product;

public interface ShelvesRepository {
    ShelfWithProducts get(int shelfNumber);
}
