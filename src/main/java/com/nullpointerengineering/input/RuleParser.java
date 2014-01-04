package com.nullpointerengineering.input;

import com.nullpointerengineering.model.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Reads the markup rules line by line
 */
public class RuleParser implements RuleRepository, Parser {

    public static final String BADLY_FORMATTED_RULE = "Illegal rule format";

    private final Collection<FinancialRule> rules = new HashSet<>();
    private final FinancialRuleFactory ruleFactory;

    public RuleParser(FinancialRuleFactory financialRuleFactory) {
        this.ruleFactory = financialRuleFactory;
    }

    /**
     * Consume a line of text and generate a new rule for each line successfully parsed
     * @param line Input line formatted as rule_subtype_ruleType=(value) e.g. labor_markup=4.33
     */
    @Override
    public void parse(String line) {
        checkInputFormat(line);

        String firstPart = line.split("=")[0];
        int lastUnderscore = firstPart.lastIndexOf('_');
        String type = firstPart.substring(lastUnderscore + 1);
        String subtype = firstPart.substring(0, lastUnderscore);
        subtype = subtype.replace('_', ' ').toLowerCase();
        BigDecimal value = new BigDecimal(line.split("=")[1]);

        rules.add(ruleFactory.buildRule(type, subtype, value ));
    }

    /**
     * Get a Collection of the rules parsed.
     * @return Collection of {@link FinancialRule}
     */
    @Override
    public Collection<FinancialRule> getRules() {
        return rules;
    }

    private void checkInputFormat(String dataLine) {
        if (!dataLine.matches("[[a-zA-Z]+_[a-zA-Z]+]+_[a-zA-Z]*=\\d+\\.?\\d*$")) {
            throw new IllegalArgumentException(BADLY_FORMATTED_RULE + ": " + dataLine);
        }
    }

}