package tdd.vendingMachine.domain.product

import spock.lang.Specification
import tdd.vendingMachine.domain.product.exception.NoSuchShelfException

class RackOfShelvesTest extends Specification {

    def "returns shelf from repository"() {
        given:
            repository.get(SHELF_NUMBER) >> shelfWithProducts
        and:
            shelfWithProducts.getNumberOfProducts() >> NON_EMPTY
        when:
            def result = rack.findNonEmptyShelf(SHELF_NUMBER)
        then:
            result == shelfWithProducts
    }

    def "throws exception on null from repository "() {
        given:
            repository.get(SHELF_NUMBER) >> null
        when:
            rack.findNonEmptyShelf(SHELF_NUMBER)
        then:
            thrown(NoSuchShelfException)
    }

    def "throws exception on empty shelf"() {
        given:
            repository.get(SHELF_NUMBER) >> shelfWithProducts
        and:
            shelfWithProducts.getNumberOfProducts() >> EMPTY
        when:
            def result = rack.findNonEmptyShelf(SHELF_NUMBER)
        then:
            thrown(NoSuchShelfException)
    }

    int SHELF_NUMBER = 13
    int NON_EMPTY = 3
    int EMPTY = 0
    ShelvesRepository repository = Mock(ShelvesRepository)
    ShelfWithProducts shelfWithProducts = Mock(ShelfWithProducts)
    RackOfShelves rack = new RackOfShelves(repository)

}
