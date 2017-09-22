package tdd.vendingMachine.domain.cash

import spock.lang.Specification
import tdd.vendingMachine.domain.Money

import static tdd.vendingMachine.domain.cash.CashDepositTest.CashDepositAssertion.assertThat

class CashDepositTest extends Specification {

    def "Stores valid coin"() {
        given:
            Money validCoin = Coin.HALF.getMonetaryValue()
        when:
            boolean wasValid = deposit.store(validCoin)
            deposit.store(Coin.FIFTH.monetaryValue)
        then:
            wasValid == true
            assertThat(deposit).contains(1, Coin.HALF)
    }


    def "Does not store invalid coin"() {
        given:
            Money invalidCoin = Money.from(10, 0)
            assert !Coin.isValidDenomination(invalidCoin)
        when:
            boolean wasValid = deposit.store(invalidCoin)
        then:
            wasValid == false
            assertThat(deposit).isEmpty()
    }


    def "Does change via money storage machine"() {
        given:
            deposit.store(Coin.HALF.monetaryValue)
            deposit.store(Coin.HALF.monetaryValue)
            deposit.store(Coin.FIVE.monetaryValue)
        when:
            deposit.refund(Money.from(1, 0))
        then:
            1 * moneyStorageMachine.releaseCoins(_) >> { arguments ->
                assert arguments[0] == [Coin.HALF, Coin.HALF]
            }
    }

    def "Decreases amount of coins in own record"() {
        given:
            deposit.store(Coin.HALF.monetaryValue)
            deposit.store(Coin.HALF.monetaryValue)
            deposit.store(Coin.FIVE.monetaryValue)
        when:
            deposit.refund(Money.from(1, 0))
        then:
            assertThat(deposit).contains(0, Coin.HALF)
    }

    def "Throws exception if can't refund the amount"() {
        given:
            deposit.store(Coin.FIVE.monetaryValue)
        when:
            deposit.refund(Money.from(1, 0))
        then:
            thrown(RefundException)
    }

    MoneyStorageMachine moneyStorageMachine = Mock(MoneyStorageMachine)
    CashDeposit deposit = new CashDeposit(moneyStorageMachine)


    static class CashDepositAssertion {
        CashDeposit cashDeposit

        static CashDepositAssertion assertThat(CashDeposit cashDeposit) {
            return new CashDepositAssertion(cashDeposit)
        }

        CashDepositAssertion(CashDeposit cashDeposit) {
            this.cashDeposit = cashDeposit
        }

        CashDepositAssertion contains(int numberOfCoins, Coin coin) {
            assert cashDeposit.storedCoins.get(coin) == numberOfCoins
            return this
        }

        CashDepositAssertion isEmpty() {
            assert cashDeposit.storedCoins.values().sum() == 0
            return this
        }
    }
}
