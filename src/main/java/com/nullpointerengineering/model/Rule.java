package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Financial rules that can be applied to an order to generate a price adjustment
 */
public interface Rule {

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public static final RoundingMode RULE_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public static final int RULE_SCALE = 2;
    /**
     * Applies a financial rule to an order these may return positive or negative values based on the characteristics
     * of each order
     * @param order The order to apply the rule to (will return {@code BigDecimal.ZERO} if the rule does not apply
     * @return BigDecimal scaled to two decimal points may be positive or negative and it is the change in price that
     * should be applied to the order's value.
     */
    BigDecimal applyTo(Order order);

}
