package com.nullpointerengineering.model;

import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;

import java.math.BigDecimal;

import static com.google.common.base.Charsets.UTF_8;

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
        Preconditions.checkArgument(!productType.trim().isEmpty(),"Type description cannot be empty");
        this.productType = productType;
    }

    @Override
    public void applyTo(Order order) {
        if (order.getType().equalsIgnoreCase(productType)) {
            BigDecimal adjustment = order.getBaseValue().multiply(markup).setScale(RULE_SCALE, RULE_ROUNDING_MODE);
            order.addToTotalValue(adjustment);
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
        return Hashing.md5()
                .newHasher()
                .putString(productType, UTF_8)
                .putString(markup.toString(), UTF_8)
                .hash()
                .asInt();
    }

}