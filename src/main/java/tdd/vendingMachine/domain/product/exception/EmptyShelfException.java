package tdd.vendingMachine.domain.product.exception;

public class EmptyShelfException  extends ProductException{

    public EmptyShelfException(String message) {
        super(message);
    }

}
