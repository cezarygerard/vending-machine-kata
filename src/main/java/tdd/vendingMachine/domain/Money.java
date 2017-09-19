package tdd.vendingMachine.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Money {

    private final long moneyAsCents;

    private Money(long moneyAsCents) {
        this.moneyAsCents = moneyAsCents;
    }

    private Money(long units, long cents) {
        this.moneyAsCents = units * 100 + cents;
    }

    public static Money from(long units, long cents){
        return new Money(units, cents);
    }

    public static Money from(double value){
        long cents = Math.round(value*100);
        return new Money(cents);
    }

    @Override
    public String toString() {
        return moneyAsCents/100 + ","+moneyAsCents % 100;
    }
}
