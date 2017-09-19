package tdd.vendingMachine.domain.product

import spock.lang.Specification
import tdd.vendingMachine.domain.Money
import tdd.vendingMachine.domain.product.exception.EmptyShelfException

class ShelfWithProductsTest extends Specification {

    def "Returns number of products available"() {
        given:
            int numberOfProducts = 10
        when:
            ShelfWithProducts shelf = new ShelfWithProducts(ANY_PRODUCT, numberOfProducts )
        then:
            shelf.numberOfProducts == numberOfProducts
    }

    def "Price cech on empty shelf throws exception"() {
        given:
            ShelfWithProducts shelf = new ShelfWithProducts(ANY_PRODUCT, EMPTY )
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
            ShelfWithProducts shelf = new ShelfWithProducts(product, ANY_NUMBER )
        then:
            shelf.price == price
    }

    private ProductType productWithPrice(Money price){
        Mock(ProductType){
            getPrice() >> price
        }
    }

    private final ProductType ANY_PRODUCT = Mock(ProductType)
    private final int ANY_NUMBER = 5
    private final int EMPTY = 0

}


