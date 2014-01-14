package com.nullpointerengineering.input;

import com.google.common.base.Function;
import com.nullpointerengineering.model.*;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Reads the markup rules line by line
 */
public class RuleParser implements Function<String, FinancialRule> {

    public static final String BADLY_FORMATTED_RULE = "Illegal rule format";
    public final FinancialRuleFactory ruleFactory;

    public RuleParser(FinancialRuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
    }

    @Override
    public FinancialRule apply(String line) {
        checkInputFormat(line);
        String firstPart = line.split("=")[0];
        int lastUnderscore = firstPart.lastIndexOf('_');
        String type = firstPart.substring(lastUnderscore + 1);
        String subtype = firstPart.substring(0, lastUnderscore);
        subtype = subtype.replace('_', ' ').toLowerCase();
        BigDecimal value = new BigDecimal(line.split("=")[1]);

        return ruleFactory.buildRule(type, subtype, value);
    }

    private void checkInputFormat(String dataLine) {
        if (!dataLine.matches("[[a-zA-Z]+_[a-zA-Z]+]+_[a-zA-Z]*=\\d+\\.?\\d*$")) {
            throw new IllegalArgumentException(BADLY_FORMATTED_RULE + ": " + dataLine);
        }
    }
}