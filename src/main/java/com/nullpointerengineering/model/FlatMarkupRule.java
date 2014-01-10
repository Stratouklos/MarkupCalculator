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
        this.markup = markupPercentage.divide(ONE_HUNDRED);
    }

    @Override
    public BigDecimal applyTo(Order order) {
        return order.getOrderValue().multiply(markup).setScale(RULE_SCALE, RULE_ROUNDING_MODE);
    }

    @Override
    public String toString() {
        return String.format("Flat markup rule of %s percent",  markup.multiply(ONE_HUNDRED).toPlainString());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof FlatMarkupRule;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}