package tdd.vendingMachine.domain

import spock.lang.Specification
import tdd.vendingMachine.domain.product.RackOfShelves
import tdd.vendingMachine.domain.product.exception.ProductException
import tdd.vendingMachine.domain.transaction.PurchaseSessionFactory

import static tdd.vendingMachine.domain.VendingMachine.NO_PRODUCT

class VendingMachineTest extends Specification {

    def "Selecting product from shelf displays its price"() {
        given:
            int shelfNumber = 3
            Money price = Money.from(2, 50)
            shelves.priceOfProductAt(shelfNumber) >> price
        when:
            machine.selectProductFrom(shelfNumber)
        then:
            1 * display.showText(price.toString())
    }

    def "Error in acquiring price results in error message"() {
        given:
            shelves.priceOfProductAt(ANY_NUMBER) >> {throw new ProductException()}
        when:
            machine.selectProductFrom(ANY_NUMBER)
        then:
            1 * display.showText(NO_PRODUCT)
    }

//    def "Selecting product from shelf starts transaction"() {
//        when:
//            machine.selectProductFrom(ANY_NUMBER)
//        then:
//            1 *
//    }

    def "Selecting product from shelf having transaction started displays message"() {

    }

    def setup(){
//        shelves.priceOfProductAt(_) >> ANY_PRICE
    }

    Money ANY_PRICE = Money.from(2.2)

    int ANY_NUMBER = 5

    RackOfShelves shelves = Mock(RackOfShelves)

    Display display = Mock(Display)

    PurchaseSessionFactory purchaseSessionFactory = Mock(PurchaseSessionFactory)

    VendingMachine machine = new VendingMachine(shelves, display, purchaseSessionFactory)
}
