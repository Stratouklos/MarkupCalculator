package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class FlatMarkupRuleTest {

    FinancialRuleFactory ruleFactory = new FinancialRuleFactory();
    FinancialRule ruleUnderTest;
    Order mockOrder = mock(OrderImpl.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFivePercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(5));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("5.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testZeroPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", ZERO);

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testMinusTenPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(-10));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("-10.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testOneHundredAndTenPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(110));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("110.01");
        assertThat(actual, is(expected));
    }

    @Test
    public void testThreeThousandPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(3000));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("3000.30");
        assertThat(actual, is(expected));
    }

    @Test
    public void testPointZeroOnePercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(0.01));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.01");
        assertThat(actual, is(expected));
    }

    @Test
    public void testPointZeroZeroZeroOnePercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(0.0001));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testHashCode() {
        FinancialRule rule1 = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(5));
        FinancialRule rule2 = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(5));

        assertEquals(rule1.hashCode(), rule2.hashCode());
    }

    @Test
    public void testEquals() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(5));
        FinancialRule rule1 = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(5));

        assertEquals(rule1, rule2);
        assertEquals(rule2, rule1);
    }

    @Test
    public void testNullMarkupThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new FlatMarkupRule(null);
    }

}