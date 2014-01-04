package com.nullpointerengineering.model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Financial rule applied to specific types of orders.
 */
public class ProductTypeMarkupRule implements FinancialRule {

    private final BigDecimal markup;
    private final String productType;

    public ProductTypeMarkupRule(BigDecimal markupPercentage, String productType) {
        this.markup = markupPercentage.divide(ONE_HUNDRED);
        if(productType.trim().isEmpty()) throw new IllegalArgumentException("Type description cannot be empty");
        this.productType = productType;
    }

    @Override
    public BigDecimal applyTo(Order order) {
        if (order.getType().equalsIgnoreCase(productType)) {
            return order.getOrderValue().multiply(markup).setScale(RULE_SCALE, RULE_ROUNDING_MODE);
        } else {
            return BigDecimal.ZERO.setScale(RULE_SCALE);
        }
    }

}
