package com.nullpointerengineering.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static java.math.BigDecimal.ONE;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class FinancialRuleComparatorTest {

    FinancialRuleComparator comparatorUnderTest;

    FinancialRule flatRule = new FlatMarkupRule(ONE);
    FinancialRule laborRule = new LaborMarkupRule(ONE);
    FinancialRule typeRule = new ProductTypeMarkupRule(ONE, "things");


    @Test
    public void testSingleRule() {
        comparatorUnderTest = FinancialRuleComparator.first(FlatMarkupRule.class);
        assertTrue(comparatorUnderTest.compare(flatRule, laborRule) > 0);
    }

    @Test
    public void testSingleRuleReverse() {
        comparatorUnderTest = FinancialRuleComparator.first(FlatMarkupRule.class);
        assertTrue(comparatorUnderTest.compare(laborRule, flatRule) < 0);
    }

    @Test
    public void testEqualRules() {
        comparatorUnderTest = FinancialRuleComparator.first(FlatMarkupRule.class);
        assertTrue(comparatorUnderTest.compare(flatRule, flatRule) == 0);
    }

    @Test
    public void testTwoRules() {
        comparatorUnderTest = FinancialRuleComparator.first(FlatMarkupRule.class).then(LaborMarkupRule.class);
        assertTrue(comparatorUnderTest.compare(typeRule, laborRule) < 0);
    }

    @Test
    public void testLowPriorityRules() {
        comparatorUnderTest = FinancialRuleComparator.first(FlatMarkupRule.class);
        assertTrue(comparatorUnderTest.compare(typeRule, typeRule) == 0);
    }
}