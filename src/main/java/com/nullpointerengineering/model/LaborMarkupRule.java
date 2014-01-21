package com.nullpointerengineering.model;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
*  Calculates a markup according to the workers involved
 */

public class LaborMarkupRule implements FinancialRule {

    private final BigDecimal markupPercentagePerWorker;

    public LaborMarkupRule(BigDecimal markupPercentagePerWorker) {
        if (markupPercentagePerWorker == null) throw new NullPointerException();
        this.markupPercentagePerWorker = markupPercentagePerWorker;
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
        int hash = 603;
        hash = hash * 76 + markupPercentagePerWorker.hashCode();
        return hash;
    }

}
