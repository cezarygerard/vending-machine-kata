package tdd.vendingMachine.domain.product

import spock.lang.Specification


class RackOfShelvesTest extends Specification {

    def "returns price of product at existing shelf"() {
        given:
            Map<Integer, ShelfWithProducts> shelves = Mock(Map) {
                get(EXISTING)>> shelfWithProducts()
            }
        when:
            RackOfShelves rack = new RackOfShelves(shelves);
        then:
            rack.priceOfProductAt(EXISTING) == shelfWithProducts().getPrice().value
    }

    def "price request of non existing shelf throws exception"() {
        given:
            Map<Integer, ShelfWithProducts> shelves = Mock(Map) {
                get(NON_EXISTING)>> null
            }
            RackOfShelves rack = new RackOfShelves(shelves);
        when:
            rack.priceOfProductAt(NON_EXISTING)
        then:
            thrown IllegalArgumentException
    }

    Integer EXISTING = 3
    Integer NON_EXISTING = 13

    ShelfWithProducts shelfWithProducts() {
        Mock(ShelfWithProducts){
            getPrice() >> Price.of(BigDecimal.ONE)
        }
    }

}
