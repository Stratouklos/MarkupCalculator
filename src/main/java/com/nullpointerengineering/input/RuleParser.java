package com.nullpointerengineering.input;

import com.nullpointerengineering.model.FinancialRule;
import com.nullpointerengineering.model.FlatMarkupRule;
import com.nullpointerengineering.model.LaborMarkupRule;
import com.nullpointerengineering.model.ProductTypeMarkupRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Reads the markup rules line by line
 */
public class RuleParser implements RuleRepository, Parser {

    public static final String NON_MARKUP_RULES_NOT_SUPPORTED = "Non markup rules are not supported... yet";
    public static final String TOO_MANY_WORDS_AS_TYPE_DESCRIPTIONS = "Cannot use more than one word to describe types";
    public static final String BADLY_FORMATTED_RULE = "Illegal rule format";

    private Collection<FinancialRule> rules = new ArrayList<>();

    /**
     * Consume a line of text and generate a new rule for each line successfully parsed
     * @param line Input line formatted as rule_subtype_ruleType=(value) e.g. labor_markup=4.33
     */
    @Override
    public void parse(String line) {
        checkInputFormat(line);

        String type = checkAndGetType(line.split("=")[0]);
        BigDecimal value = new BigDecimal(line.split("=")[1]);

        if (type.equalsIgnoreCase("flat"))
            rules.add(new FlatMarkupRule(value));
        else if (type.equalsIgnoreCase("labor"))
            rules.add(new LaborMarkupRule(value));
        else if (type.contains("_"))
            throw new IllegalArgumentException(TOO_MANY_WORDS_AS_TYPE_DESCRIPTIONS);
        else
            rules.add(new ProductTypeMarkupRule(value, type.toLowerCase()));
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

    private String checkAndGetType(String ruleName) {
        int lastUnderscore = ruleName.lastIndexOf('_');

        if (!ruleName.substring(lastUnderscore + 1).equalsIgnoreCase("markup"))
            throw new IllegalArgumentException(NON_MARKUP_RULES_NOT_SUPPORTED);

        return ruleName.substring(0,lastUnderscore);
    }

}