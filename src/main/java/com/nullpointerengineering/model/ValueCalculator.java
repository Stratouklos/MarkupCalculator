package com.nullpointerengineering.model;

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

    public String calculateTotalValue(Order order) {
        for (FinancialRule rule : rules) {
            rule.applyTo(order);
        }
        return String.format("$%(.2f", order.getTotalValue());
    }

}