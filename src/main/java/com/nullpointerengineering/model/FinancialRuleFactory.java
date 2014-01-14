package com.nullpointerengineering.model;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Decouples rule readers from the rule hierarchy
 */
public class FinancialRuleFactory {

    public static final String UNSUPPORTED_TYPE = "Type is not supported";


    public FinancialRule buildRule(String type, String subtype, BigDecimal value) {
        checkArgument(type.equalsIgnoreCase("markup"), UNSUPPORTED_TYPE + ": " + type);
        if (subtype.equalsIgnoreCase("flat"))
            return new FlatMarkupRule(value);
        else if (subtype.equalsIgnoreCase("labor"))
            return new LaborMarkupRule(value);
        else
            return new ProductTypeMarkupRule(value, subtype);
    }

}