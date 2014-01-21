package com.nullpointerengineering.model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Calculates a flat markup
 */
public class FlatMarkupRule implements FinancialRule {

    private final BigDecimal markup;

    public FlatMarkupRule(BigDecimal markupPercentage) {
        if (markupPercentage == null) throw new NullPointerException();
        this.markup = markupPercentage;
    }

    @Override
    public void applyTo(Order order) {
        order.addToBaseValue(order.getBaseValue().applyPercentage(markup));
    }

    @Override
    public boolean isTypeOf(FinancialRule rule) {
        return rule instanceof FlatMarkupRule;
    }

    @Override
    public String toString() {
        return String.format("Flat markup rule of %.2f percent",  markup);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (! (object instanceof FlatMarkupRule)) return false;
        FlatMarkupRule that = (FlatMarkupRule) object;

        return this.markup.equals(that.markup);
    }

    @Override
    public int hashCode() {
        int hash = 403;
        hash = hash * 45 + markup.hashCode();
        return hash;
    }

}