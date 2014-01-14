package com.nullpointerengineering.model;

import com.google.common.hash.Hashing;

import java.math.BigDecimal;

import static com.google.common.base.Charsets.UTF_8;

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
    public void applyTo(Order order) {
        BigDecimal baseValueAdjustment = order.getBaseValue().multiply(markup).setScale(RULE_SCALE, RULE_ROUNDING_MODE);
        order.addToBaseValue(baseValueAdjustment);
    }

    @Override
    public boolean isTypeOf(FinancialRule rule) {
        return rule instanceof FlatMarkupRule;
    }

    @Override
    public String toString() {
        return String.format("Flat markup rule of %s percent",  markup.multiply(ONE_HUNDRED).toPlainString());
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
        return Hashing.md5()
                .newHasher()
                .putString(markup.toString(), UTF_8)
                .hash()
                .asInt();
    }

}