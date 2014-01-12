package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Calculates value adjustments for orders based on financial rules.
 */
public class ValueCalculator {

    private final Collection<FinancialRule> rules;

    public ValueCalculator(Collection<FinancialRule> rules) {
        this.rules = rules;
    }

    public BigDecimal calculateTotalValue(Order order) {
        for (FinancialRule rule : rules) {
            rule.applyTo(order);
        }
        return order.getTotalValue();
    }

}