package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class FlatMarkupRuleTest {

    FinancialRuleFactory ruleFactory = new FinancialRuleFactory();
    FinancialRule ruleUnderTest;
    Order mockOrder = mock(OrderWithTrails.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFivePercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(5));

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);
        verify(mockOrder).addToBaseValue(new ImmutableMoney("5"));
    }

    @Test
    public void testZeroPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", ZERO);

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToBaseValue(new ImmutableMoney("0"));
    }

    @Test
    public void testMinusTenPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(-10));

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);
        verify(mockOrder).addToBaseValue(new ImmutableMoney("-10"));
    }

    @Test
    public void testOneHundredAndTenPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(110));

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToBaseValue(new ImmutableMoney("110.01"));
    }

    @Test
    public void testThreeThousandPercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(3000));

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToBaseValue(new ImmutableMoney("3000.30"));
    }

    @Test
    public void testPointZeroOnePercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(0.01));

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToBaseValue(new ImmutableMoney("0.01"));
    }

    @Test
    public void testPointZeroZeroZeroOnePercent() {
        ruleUnderTest = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(0.0001));

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToBaseValue(new ImmutableMoney("0"));
    }

    @Test
    public void testTypeOf() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(1));
        FinancialRule rule1 = ruleFactory.buildRule("markup", "flat", BigDecimal.valueOf(6));

        assertTrue(rule1.isTypeOf(rule2));
        assertTrue(rule2.isTypeOf(rule1));
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

    @Test
    public void testToString() {
        FinancialRule rule = ruleFactory.buildRule("markup", "flat", valueOf(5));

        assertThat(rule.toString(), is("Flat markup rule of 5.00 percent"));
    }

}