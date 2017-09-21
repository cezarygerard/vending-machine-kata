package tdd.vendingMachine.domain

import spock.lang.Specification

class MoneyTest extends Specification {

    def "Subtracts two money amounts"() {
        given:
            double firstAmount = 6.8
            double secondAmount = 11.3
        and:
            Money firstMoney = Money.from(firstAmount)
            Money secondMoney = Money.from(secondAmount)
        when:
            Money result = firstMoney.subtract(secondMoney)
        then:
            result == Money.from(firstAmount - secondAmount)
    }

    def "Subtruction result is new instance"() {
        given:
            Money firstMoney = Money.from(6)
            Money secondMoney = Money.from(7)
        when:
            Money result = firstMoney.subtract(secondMoney)
        then:
            !result.is(firstMoney)
            !result.is(secondMoney)
    }

    def "Adds two money amounts"() {
        given:
            double firstAmount = 6.8
            double secondAmount = 11.3
        and:
            Money firstMoney = Money.from(firstAmount)
            Money secondMoney = Money.from(secondAmount)
        when:
            Money result = firstMoney.add(secondMoney)
        then:
            result == Money.from(firstAmount + secondAmount)
    }

    def "Addition result is new instance"() {
        given:
            Money firstMoney = Money.from(6)
            Money secondMoney = Money.from(7)
        when:
            Money result = firstMoney.add(secondMoney)
        then:
            !result.is(firstMoney)
            !result.is(secondMoney)
    }


}

