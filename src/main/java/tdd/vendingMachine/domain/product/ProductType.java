package tdd.vendingMachine.domain.product;

import lombok.*;
import tdd.vendingMachine.domain.Money;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter(AccessLevel.PACKAGE)
class ProductType {

    private final Money price;

    private final String productDescription;

}
