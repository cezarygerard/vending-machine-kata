package tdd.vendingMachine.domain.product.exception;

public class ProductException extends RuntimeException {

    public ProductException(String message) {
        super(message);
    }

    public ProductException() {
    }
}
