package com.nullpointerengineering.model;

import com.google.common.base.Function;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Calculates value adjustments for orders based on financial rules.
 */
public class ValueCalculator implements Function<Order, String>{

    private final Collection<FinancialRule> rules;

    public ValueCalculator(Collection<FinancialRule> rules) {
        this.rules = rules;
    }

    @Override
    public String apply(Order order) {
        for (FinancialRule rule : rules) {
            rule.applyTo(order);
        }
        return String.format("$%(.2f", order.getTotalValue());
    }
}