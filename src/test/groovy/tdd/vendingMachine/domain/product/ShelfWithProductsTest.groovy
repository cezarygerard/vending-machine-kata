package tdd.vendingMachine.domain.product

import spock.lang.Specification
import tdd.vendingMachine.domain.Money
import tdd.vendingMachine.domain.product.exception.EmptyShelfException

class ShelfWithProductsTest extends Specification {

    def "Returns number of products available"() {
        given:
            int numberOfProducts = 10
        when:
            ShelfWithProducts shelf = new ShelfWithProducts(ANY_PRODUCT, numberOfProducts, ANY_ROBOT)
        then:
            shelf.numberOfProducts == numberOfProducts
    }

    def "Price cech on empty shelf throws exception"() {
        given:
            ShelfWithProducts shelf = new ShelfWithProducts(ANY_PRODUCT, EMPTY, ANY_ROBOT)
        when:
            shelf.price
        then:
            thrown EmptyShelfException
    }

    def "Returns price of the products"() {
        given:
            Money price = Money.from(3, 30)
            ProductType product = productWithPrice(price)
        when:
            ShelfWithProducts shelf = new ShelfWithProducts(product, ANY_NUMBER, ANY_ROBOT)
        then:
            shelf.price == price
    }


    def "Dispenses product via ProductReleaseRobot"() {
        given:
            ProductReleaseRobot robot  = Mock(ProductReleaseRobot)
        and:
            ShelfWithProducts shelf = new ShelfWithProducts(ANY_PRODUCT, ANY_NUMBER, robot)
        when:
            shelf.dispense()
        then:
             1 * robot.releaseProductFrom(shelf)
    }

    private ProductType productWithPrice(Money price) {
        Mock(ProductType) {
            getPrice() >> price
        }
    }


    ProductType ANY_PRODUCT = Mock(ProductType)
    ProductReleaseRobot ANY_ROBOT  = Mock(ProductReleaseRobot)
    int ANY_NUMBER = 5
    int EMPTY = 0


}


