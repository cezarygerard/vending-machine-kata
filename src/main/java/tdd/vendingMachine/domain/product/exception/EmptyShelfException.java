package tdd.vendingMachine.domain.product.exception;

public class EmptyShelfException  extends RuntimeException{

    public EmptyShelfException(String message) {
        super(message);
    }

}
