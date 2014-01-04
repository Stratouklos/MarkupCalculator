package com.nullpointerengineering.module;

import com.nullpointerengineering.input.LineReaderFromFile;
import com.nullpointerengineering.input.RuleParser;
import com.nullpointerengineering.model.*;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * FinancialRule parsing module tests
 */
@RunWith(JUnit4.class)
public class RuleParsingModuleTest {

    public static final String ONE_RULE_FILE = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\one_rule";
    public static final String FIVE_RULES_FILE = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\five_rules";

    LineReaderFromFile lineReaderFromFile;

    RuleParser ruleParser;

    @Before
    public void setup() {
        ruleParser = new RuleParser(new FinancialRuleFactory());
    }

    @Test
    public void readOneRule() throws IOException {
        lineReaderFromFile = new LineReaderFromFile(ONE_RULE_FILE);
        lineReaderFromFile.readIntoParser(ruleParser);
        Collection<FinancialRule> rules = ruleParser.getRules();
        assertThat(rules.size(), CoreMatchers.is(1));
    }

    @Test
    public void readFiveRules() throws IOException {
        lineReaderFromFile = new LineReaderFromFile(FIVE_RULES_FILE);
        lineReaderFromFile.readIntoParser(ruleParser);
        Collection<FinancialRule> rules = ruleParser.getRules();
        assertThat(rules.size(), CoreMatchers.is(5));
    }

    @Test
    public void testRulesReadCorrectly() throws IOException {
        lineReaderFromFile = new LineReaderFromFile(FIVE_RULES_FILE);
        lineReaderFromFile.readIntoParser(ruleParser);
        Collection<FinancialRule> rules = ruleParser.getRules();

        assertThat(rules, hasItems(
            new FlatMarkupRule(BigDecimal.valueOf(5)),
            new LaborMarkupRule(BigDecimal.valueOf(1.2)),
            new ProductTypeMarkupRule(BigDecimal.valueOf(7.5), "drugs"),
            new ProductTypeMarkupRule(BigDecimal.valueOf(13), "food"),
            new ProductTypeMarkupRule(BigDecimal.valueOf(2), "electronics")));

    }

}
