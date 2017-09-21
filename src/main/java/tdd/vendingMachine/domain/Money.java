package tdd.vendingMachine.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Money implements Comparable<Money> {

    public static final Money ZERO = Money.from(0,0);

    private final long cents;

    private Money(long cents) {
        this.cents = cents;
    }

    private Money(long units, long cents) {
        this.cents = units * 100 + cents;
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
        return cents /100 + ","+ cents % 100;
    }

    public Money subtract(Money amount) {
        return new Money(this.cents - amount.cents);
    }

    public Money add(Money amount) {
        return new Money(this.cents + amount.cents);
    }


    @Override
    public int compareTo(Money other) {
        return Long.compare(this.cents, other.cents);
    }
}
