package tdd.vendingMachine.domain

import spock.lang.Specification
import tdd.vendingMachine.domain.product.exception.ProductException
import tdd.vendingMachine.domain.transaction.PurchaseProcess

class VendingMachineTest extends Specification {

    def "Error in acquiring price results in error message"() {
        given:
            purchaseSession.selectProduct(SOME_NUMBER) >> {throw new ProductException()}
        when:
            machine.selectProductFrom(SOME_NUMBER)
        then:
            1 * display.noProductFound()
    }

    def "Selecting product from shelf starts new transaction"() {
        when:
            machine.selectProductFrom(SOME_NUMBER)
        then:
            1 * purchaseSession.selectProduct(SOME_NUMBER)
    }

    def "Displays price after starting  transaction"() {
        given:
            purchaseSession.amountLeftToPay() >> SOME_MONEY
        when:
            machine.selectProductFrom(SOME_NUMBER)
        then:
            1 * display.price(SOME_MONEY)
    }

    def "Inserting money sends it to session"() {
        when:
            machine.insertMoney(SOME_MONEY)
        then:
            1 * purchaseSession.insert(SOME_MONEY)
    }

    def "Inserting money displays amount left"() {
        given:
            purchaseSession.amountLeftToPay() >> SOME_MONEY
        when:
            machine.insertMoney(SOME_OTHER_MONEY)
        then:
            1 * display.price(SOME_MONEY)
    }

    def "Cancel clears session"() {
        when:
            machine.cancel()
        then:
            1 * purchaseSession.cancel()
    }

    def "Cancel clear display"() {
        when:
            machine.cancel()
        then:
            1 * display.selectProduct()
    }

    def "Display defaults to product selection prompt"() {
        when:
            new VendingMachine(display, purchaseSession)
        then:
            1 * display.selectProduct()
    }

    Money SOME_MONEY = Money.from(2.2)

    Money SOME_OTHER_MONEY = Money.from(1)

    int SOME_NUMBER = 5

    Display display = Mock(Display)

    PurchaseProcess purchaseSession = Mock(PurchaseProcess)

    VendingMachine machine = new VendingMachine(display, purchaseSession)
}
