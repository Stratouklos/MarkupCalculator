package com.nullpointerengineering.model;

import java.math.BigDecimal;

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
    public BigDecimal applyTo(Order order) {
        BigDecimal totalMarkup  = markup.multiply(BigDecimal.valueOf(order.getWorkers()));
        return order.getOrderValue().multiply(totalMarkup).setScale(RULE_SCALE, RULE_ROUNDING_MODE);
    }

    //Added for testing purposes
    public BigDecimal getMarkup() {
        return markup;
    }

}
