package com.nullpointerengineering.module;

import com.nullpointerengineering.data.OrderedRuleRepository;
import com.nullpointerengineering.data.RuleRepository;
import com.nullpointerengineering.input.LineReaderFromFile;
import com.nullpointerengineering.input.RuleParser;
import com.nullpointerengineering.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import static com.nullpointerengineering.TestResources.FIVE_RULES_FILE;
import static com.nullpointerengineering.TestResources.ONE_RULE_FILE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * FinancialRule parsing module tests
 */
@RunWith(JUnit4.class)
public class RuleParsingModuleTest {


    LineReaderFromFile lineReaderFromFile;
    FinancialRuleComparator  comparator  = FinancialRuleComparator.first(FlatMarkupRule.class);
    RuleRepository ruleRepository = new OrderedRuleRepository(comparator);

    RuleParser ruleParser = new RuleParser(new FinancialRuleFactory(), ruleRepository);

    @Test
    public void readOneRule() throws IOException {
        lineReaderFromFile = new LineReaderFromFile(ONE_RULE_FILE);
        lineReaderFromFile.read(ruleParser);
        Collection<FinancialRule> rules = ruleRepository.getRules();
        assertThat(rules.size(), is(1));
    }

    @Test
    public void readFiveRules() throws IOException {
        lineReaderFromFile = new LineReaderFromFile(FIVE_RULES_FILE);
        lineReaderFromFile.read(ruleParser);
        Collection<FinancialRule> rules = ruleRepository.getRules();
        assertThat(rules.size(), is(5));
    }

    @Test
    public void testRulesReadCorrectly() throws IOException {
        lineReaderFromFile = new LineReaderFromFile(FIVE_RULES_FILE);
        lineReaderFromFile.read(ruleParser);
        Iterator<FinancialRule> rules = ruleRepository.getRules().iterator();

        FinancialRule flatMarkupRule = new FlatMarkupRule(BigDecimal.valueOf(5));
        FinancialRule laborMarkupRule = new LaborMarkupRule(BigDecimal.valueOf(1.2));
        FinancialRule drugsMarkupRule = new ProductTypeMarkupRule(BigDecimal.valueOf(7.5), "drugs");
        FinancialRule foodMarkupRule = new ProductTypeMarkupRule(BigDecimal.valueOf(13), "food");
        FinancialRule electronicsMarkupRule = new ProductTypeMarkupRule(BigDecimal.valueOf(2), "electronics");

        assertThat(rules.next(), is(flatMarkupRule));
        assertThat(rules.next(), is(laborMarkupRule));
        assertThat(rules.next(), is(drugsMarkupRule));
        assertThat(rules.next(), is(foodMarkupRule));
        assertThat(rules.next(), is(electronicsMarkupRule));
    }

}
