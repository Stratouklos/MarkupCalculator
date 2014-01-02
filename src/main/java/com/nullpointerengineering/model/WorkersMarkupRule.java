package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
*  Calculates a markup according to the workers involved
 */

public class WorkersMarkupRule implements Rule{

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final BigDecimal markup;

    public WorkersMarkupRule(BigDecimal markupPercentagePerWorker) {
        this.markup = markupPercentagePerWorker.divide(ONE_HUNDRED);
    }

    @Override
    public BigDecimal applyTo(Order order) {
        BigDecimal totalMarkup  = markup.multiply(BigDecimal.valueOf(order.getWorkers()));
        return order.getOrderValue().multiply(totalMarkup).setScale(2, RoundingMode.HALF_EVEN);
    }
}
