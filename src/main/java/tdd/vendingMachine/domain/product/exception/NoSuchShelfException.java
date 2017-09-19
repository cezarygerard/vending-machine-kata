package tdd.vendingMachine.domain.product.exception;

public class NoSuchShelfException extends ProductException{

    public NoSuchShelfException(String message) {
        super(message);
    }
}
