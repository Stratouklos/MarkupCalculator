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

    private final BigDecimal markupPercentagePerWorker;

    public LaborMarkupRule(BigDecimal markupPercentagePerWorker) {
        this.markupPercentagePerWorker = markupPercentagePerWorker.divide(ONE_HUNDRED);
    }

    @Override
    public void applyTo(Order order) {
        BigDecimal totalMarkup  = markupPercentagePerWorker.multiply(valueOf(order.getWorkers()));
        order.addToTotalValue(order.getBaseValue().applyPercentage(totalMarkup));
    }

    @Override
    public boolean isTypeOf(FinancialRule rule) {
        return  (rule instanceof LaborMarkupRule);
    }

    @Override
    public String toString() {
        return String.format("Labor markup rule of %.2f percent",  markupPercentagePerWorker);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (! (object instanceof LaborMarkupRule)) return false;
        LaborMarkupRule that = (LaborMarkupRule) object;

        return this.markupPercentagePerWorker.equals(that.markupPercentagePerWorker);
    }

    @Override
    public int hashCode() {
        return Hashing.md5()
                .newHasher()
                .putString(markupPercentagePerWorker.toString(), UTF_8)
                .hash()
                .asInt();
    }

}
