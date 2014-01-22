package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Type to encapsulate the idea of money
 */
public interface Money {

    Money applyPercentage(BigDecimal decimal);
    Money add(Money moreMoney);
    BigDecimal getValue();
    Currency getCurrency();

}
