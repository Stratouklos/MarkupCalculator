package com.nullpointerengineering.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;
import java.util.LinkedList;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
*/
@RunWith(JUnit4.class)
public class ValueCalculatorTest {

    ValueCalculator calculatorUnderTest;
    Collection<FinancialRule> mockRules = new LinkedList<>();

    Order mockOrder = mock(Order.class);

    private Collection<FinancialRule> getMockRules(int numberOfRules) {
        Collection<FinancialRule> mockRules = new LinkedList<>();
        for (int i = 0; i < numberOfRules; i++) {
            FinancialRule mockRule = mock(FinancialRule.class);
            mockRules.add(mockRule);
        }
        return mockRules;
    }

    private void verifyMockRules(Collection<FinancialRule> mocks) {
        for (FinancialRule mock : mocks) {
            verify(mock, times(1)).applyTo(mockOrder);
        }
    }

    @Test
    public void testSingleRule() {
        mockRules = getMockRules(1);
        calculatorUnderTest = new ValueCalculator(mockRules);

        calculatorUnderTest.calculateTotalValue(mockOrder);

        verifyMockRules(mockRules);
    }

    @Test
    public void testTenRules() {
        mockRules = getMockRules(10);
        calculatorUnderTest = new ValueCalculator(mockRules);

        calculatorUnderTest.calculateTotalValue(mockOrder);

        verifyMockRules(mockRules);
    }

    @Test
    public void testCalculateMethod() {
        calculatorUnderTest = new ValueCalculator(mockRules);

        when(mockOrder.getTotalValue()).thenReturn(ONE);
        String actual = calculatorUnderTest.calculateTotalValue(mockOrder);

        assertThat(actual, is("$1.00"));
    }

    @Test
    public void testCalculateMethodWithNegativeResult() {
        calculatorUnderTest = new ValueCalculator(mockRules);

        when(mockOrder.getTotalValue()).thenReturn(valueOf(-1));
        String actual = calculatorUnderTest.calculateTotalValue(mockOrder);

        assertThat(actual, is("$(1.00)"));
    }

}