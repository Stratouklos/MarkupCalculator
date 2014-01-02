package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Calculates a flat markup
 */
public class FlatMarkupRule implements Rule {

    private final BigDecimal markup;

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public FlatMarkupRule(BigDecimal markupPercentage) {
        this.markup = markupPercentage.divide(ONE_HUNDRED);
    }

    @Override
    public BigDecimal applyTo(Order order) {
        return order.getOrderValue().multiply(markup).setScale(2, RoundingMode.HALF_EVEN);
    }

}