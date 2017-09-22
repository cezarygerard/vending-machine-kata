package tdd.vendingMachine.domain.transaction

import spock.lang.Specification
import tdd.vendingMachine.domain.Money
import tdd.vendingMachine.domain.cash.CashDeposit
import tdd.vendingMachine.domain.cash.InvalidCoinException
import tdd.vendingMachine.domain.cash.RefundException
import tdd.vendingMachine.domain.product.RackOfShelves
import tdd.vendingMachine.domain.product.ShelfWithProducts

class PurchaseProcessTest extends Specification {

    def "Selecting product stores amount to pay"() {
        given:
            PurchaseProcess purchaseProcess = emptyProcess()
        and:
            shelves.findNonEmptyShelf(SOME_SHELF_WITH_PRODUCT) >> shelfWithProducts
            shelfWithProducts.getPrice() >> SOME_PRICE
        when:
            purchaseProcess.selectProduct(SOME_SHELF_WITH_PRODUCT)
        then:
            purchaseProcess.amountLeftToPay() == SOME_PRICE
    }

    def "inserting money decreases amount left"() {
        given:
            PurchaseProcess purchaseProcess = processWithSelectedProduct()
        when:
            Money beforeInsertingCoin = purchaseProcess.amountLeftToPay()
            purchaseProcess.insert(COIN_2)
            Money afterInsertingCoin = purchaseProcess.amountLeftToPay()
        then:
            COIN_2 == beforeInsertingCoin.subtract(afterInsertingCoin)
    }

    def "cancellation zeroes amount paid and left"() {
        given:
            PurchaseProcess purchaseProcess = processWithSelectedProduct()
        when:
            purchaseProcess.cancel()
        then:
            purchaseProcess.amountLeftToPay() == Money.ZERO
            purchaseProcess.amountPaid() == Money.ZERO
    }

    def "cancellation returns paid money"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
            Money alreadyPaid = purchaseProcess.amountPaid()
        when:
            purchaseProcess.cancel()
        then:
            1 * cashDeposit.refund(alreadyPaid)
    }

    def "inserting coin stores it in deposit"() {
        given:
            PurchaseProcess purchaseProcess = processWithSelectedProduct()
        when:
            purchaseProcess.insert(COIN_2)
        then:
            1 * cashDeposit.store(COIN_2)
    }

    def "inserting invalid coin does not decreases amount left"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
        and:
            cashDeposit.store(COIN_2) >> {throw new InvalidCoinException()}
        when:
            Money amountLeftBeforeInsertion = purchaseProcess.amountLeftToPay()
            purchaseProcess.insert(COIN_2)
        then:
            amountLeftBeforeInsertion == purchaseProcess.amountLeftToPay()
    }

    def "inserting full amount dispenses the product"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
        when:
            purchaseProcess.insert(purchaseProcess.amountLeftToPay())
        then:
            1 * shelfWithProducts.dispense()
    }

    def "inserting excessive amount refunds the change and dispenses the product"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
        when:
            purchaseProcess.insert(purchaseProcess.amountLeftToPay().add(COIN_2))
        then:
            1 * cashDeposit.refund(COIN_2)
            1 * shelfWithProducts.dispense()
    }

    def "inserting money to cancelled session"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
        and:
            purchaseProcess.cancel()
        when:
            purchaseProcess.insert(COIN_2)
        then:
            purchaseProcess.amountPaid() == COIN_2
            purchaseProcess.amountLeftToPay() == Money.ZERO.subtract(COIN_2)
    }

    def "Error in  change refunds full amount and rethrows exception"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
            Money amountPaid = purchaseProcess.amountPaid()
        and:
            cashDeposit.refund(_) >> { throw new RefundException() }
        when:
            purchaseProcess.insert(SOME_PRICE)
        then:
            1 * cashDeposit.refund(amountPaid.add(SOME_PRICE))
            purchaseProcess.amountLeftToPay() == Money.ZERO
            purchaseProcess.amountPaid() == Money.ZERO
            thrown(RefundException)
    }

    def "Error in  change must not dispense product from shelf"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
        and:
            cashDeposit.refund(_) >> { throw new RefundException() }
        when:
            purchaseProcess.insert(SOME_PRICE)
        then:
            0 * shelfWithProducts.dispense()
            thrown(RefundException)
    }

    def "Starting  another product  switches product"() {
        given:
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
            Money amountPaid = purchaseProcess.amountPaid()
        and: //another shelf
            ShelfWithProducts anotherShelf = Mock(ShelfWithProducts)
            shelves.findNonEmptyShelf(ANOTHER_SHELF) >> anotherShelf
        and: //product with new price
            Money anotherProductPrice = Money.from(7.5)
            anotherShelf.getPrice() >> anotherProductPrice
        when:
            purchaseProcess.selectProduct(ANOTHER_SHELF)
        then:
            purchaseProcess.amountLeftToPay() == anotherProductPrice.subtract(amountPaid)
            purchaseProcess.amountPaid() == amountPaid
    }

    def "Selecting cheaper product can finish transaction"() {
        given: //process with prepaid value of 2
            PurchaseProcess purchaseProcess = processWithPrepaymentOf2()
        and:  //another shel
            ShelfWithProducts anotherShelf = Mock(ShelfWithProducts)
            shelves.findNonEmptyShelf(ANOTHER_SHELF) >> anotherShelf
        and: //product with new price
            anotherShelf.getPrice() >> COIN_1
        when:
            purchaseProcess.selectProduct(ANOTHER_SHELF)
        then:
            1 * cashDeposit.refund(COIN_1)
            1 * anotherShelf.dispense()
    }

    //TODO cover no session

    PurchaseProcess emptyProcess(){
        return new PurchaseProcess(shelves, cashDeposit)
    }

    PurchaseProcess processWithSelectedProduct() {
        PurchaseProcess purchaseProcess = emptyProcess()
        shelves.findNonEmptyShelf(SOME_SHELF_WITH_PRODUCT) >> shelfWithProducts
        shelfWithProducts.getPrice() >> SOME_PRICE
        purchaseProcess.selectProduct(SOME_SHELF_WITH_PRODUCT)
        return purchaseProcess
    }

    PurchaseProcess processWithPrepaymentOf2() {
        PurchaseProcess purchaseProcess = processWithSelectedProduct()
        purchaseProcess.insert(COIN_2)
        return purchaseProcess
    }

    int SOME_SHELF_WITH_PRODUCT = 4

    int ANOTHER_SHELF = 7

    Money SOME_PRICE = Money.from(4.0)

    Money COIN_2 = Money.from(2.0)

    Money COIN_1 = Money.from(1.0)

    RackOfShelves shelves = Mock(RackOfShelves)

    ShelfWithProducts shelfWithProducts = Mock(ShelfWithProducts)

    CashDeposit cashDeposit = Mock(CashDeposit)
}

