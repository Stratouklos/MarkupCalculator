package com.nullpointerengineering.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;
import java.util.LinkedList;

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

        calculatorUnderTest.apply(mockOrder);

        verifyMockRules(mockRules);
    }

    @Test
    public void testTenRules() {
        mockRules = getMockRules(10);
        calculatorUnderTest = new ValueCalculator(mockRules);

        calculatorUnderTest.apply(mockOrder);

        verifyMockRules(mockRules);
    }

}