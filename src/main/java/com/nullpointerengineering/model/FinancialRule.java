package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Financial rules that can be applied to an order to generate a price adjustment
 */
public interface FinancialRule {

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public static final RoundingMode RULE_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public static final int RULE_SCALE = 2;
    /**
     * Applies a financial rule to an order.
     * @param order The order to apply the rule to will do nothing if the rule does not apply.
     */
    void applyTo(Order order);

    /**
     * Checks two rules' type and subtype.
     * @param rule Rule to be compared to this
     * @return  True if they belong to the same type and subtype false otherwise.
     */
    boolean isTypeOf(FinancialRule rule);

}
