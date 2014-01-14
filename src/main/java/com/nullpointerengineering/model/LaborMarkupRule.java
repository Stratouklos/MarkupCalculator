package com.nullpointerengineering.model;

import com.google.common.hash.Hashing;

import java.math.BigDecimal;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
*  Calculates a markup according to the workers involved
 */

public class LaborMarkupRule implements FinancialRule {

    private final BigDecimal markup;

    public LaborMarkupRule(BigDecimal markupPercentagePerWorker) {
        this.markup = markupPercentagePerWorker.divide(ONE_HUNDRED);
    }

    @Override
    public void applyTo(Order order) {
        BigDecimal totalMarkup  = markup.multiply(BigDecimal.valueOf(order.getWorkers()));
        BigDecimal adjustment = order.getBaseValue().multiply(totalMarkup).setScale(RULE_SCALE, RULE_ROUNDING_MODE);
        order.addToTotalValue(adjustment);
    }

    @Override
    public boolean isTypeOf(FinancialRule rule) {
        return  (rule instanceof LaborMarkupRule);
    }

    @Override
    public String toString() {
        return String.format("Labor markup rule of %s percent",  markup.multiply(ONE_HUNDRED).toPlainString());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (! (object instanceof LaborMarkupRule)) return false;
        LaborMarkupRule that = (LaborMarkupRule) object;

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
