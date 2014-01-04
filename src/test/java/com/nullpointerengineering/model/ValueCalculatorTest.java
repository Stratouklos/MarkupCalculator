package com.nullpointerengineering.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Matchers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
*/
@RunWith(JUnit4.class)
public class ValueCalculatorTest {

    ValueCalculator calculatorUnderTest;
    Order mockOrder = mock(Order.class);

    private Collection<FinancialRule> getMockRules(int numberOfRules, BigDecimal values) {
        Collection<FinancialRule> mockRules = new LinkedList<>();
        for (int i = 0; i < numberOfRules; i++) {
            FinancialRule mockRule = mock(FinancialRule.class);
            when(mockRule.applyTo(Matchers.<Order>any())).thenReturn(values);
            mockRules.add(mockRule);
        }
        return mockRules;
    }

    @Test
    public void testSingleRule() {
        calculatorUnderTest = new ValueCalculator(getMockRules(1, BigDecimal.valueOf(10.10)));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100));

        assertThat(calculatorUnderTest.calculateTotalValue(mockOrder), is(BigDecimal.valueOf(110.10)));
    }

    @Test
    public void testTenRules() {
        calculatorUnderTest = new ValueCalculator(getMockRules(10, BigDecimal.valueOf(5)));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.0));

        assertThat(calculatorUnderTest.calculateTotalValue(mockOrder), is(BigDecimal.valueOf(150.0)));
    }

    @Test
    public void testRulesWithZeroMarkup() {
        calculatorUnderTest = new ValueCalculator(getMockRules(10, BigDecimal.valueOf(0)));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.0));

        assertThat(calculatorUnderTest.calculateTotalValue(mockOrder), is(BigDecimal.valueOf(100.0)));
    }

    @Test
    public void testRulesWithNegativeMarkup() {
        calculatorUnderTest = new ValueCalculator(getMockRules(10, BigDecimal.valueOf(-5)));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.0));

        assertThat(calculatorUnderTest.calculateTotalValue(mockOrder), is(BigDecimal.valueOf(50.0)));
    }

}