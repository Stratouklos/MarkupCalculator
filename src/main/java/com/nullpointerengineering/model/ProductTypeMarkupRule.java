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
            return order.getBaseValue().multiply(markup).setScale(RULE_SCALE, RULE_ROUNDING_MODE);
        } else {
            return BigDecimal.ZERO.setScale(RULE_SCALE);
        }
    }

    @Override
    public boolean isTypeOf(FinancialRule rule) {
        if (this == rule) return true;
        if (! (rule instanceof ProductTypeMarkupRule)) return false;
        ProductTypeMarkupRule that = (ProductTypeMarkupRule) rule;

        return this.productType.equals(that.productType);
    }

    @Override
    public String toString() {
        return String.format("Product type markup rule of %s percent for %s",
                markup.multiply(ONE_HUNDRED).toPlainString(), productType);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (! (object instanceof ProductTypeMarkupRule)) return false;
        ProductTypeMarkupRule that = (ProductTypeMarkupRule) object;

        return this.markup.equals(that.markup) &&
                this.productType.equals(that.productType);
    }

    @Override
    public int hashCode() {
        int hash = 903;
        hash = hash * 11 + markup.hashCode();
        hash = hash * 11 + productType.hashCode();
        return hash;
    }

}