package tdd.vendingMachine.domain.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@EqualsAndHashCode
@Getter(AccessLevel.PACKAGE)
class ProductType {

    private Price price;

    private String productDescription;
}
