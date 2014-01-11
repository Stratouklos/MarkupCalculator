package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 *
 */
@RunWith(JUnit4.class)
public class LaborMarkupRuleTest {

    FinancialRuleFactory ruleFactory = new FinancialRuleFactory();
    FinancialRule ruleUnderTest;
    Order mockOrder = mock(OrderImpl.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testOneWorker() {
        ruleUnderTest = ruleFactory.buildRule("markup", "labor", BigDecimal.valueOf(2));

        when(mockOrder.getWorkers()).thenReturn(1);
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("2.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testTwoWorkers() {
        ruleUnderTest = ruleFactory.buildRule("markup", "labor", BigDecimal.valueOf(2));

        when(mockOrder.getWorkers()).thenReturn(2);
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("4.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testZeroWorkers() {
        ruleUnderTest = ruleFactory.buildRule("markup", "labor", BigDecimal.valueOf(2));

        when(mockOrder.getWorkers()).thenReturn(0);
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testTypeOf() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "labor", BigDecimal.ONE);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "labor", BigDecimal.ZERO);

        assertTrue(rule1.isTypeOf(rule2));
        assertTrue(rule2.isTypeOf(rule1));
    }

    @Test
    public void testNullMarkupThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new LaborMarkupRule(null);
    }

    @Test
    public void testHashCode() {
        FinancialRule rule1 = ruleFactory.buildRule("markup", "labor", BigDecimal.valueOf(5));
        FinancialRule rule2 = ruleFactory.buildRule("markup", "labor", BigDecimal.valueOf(5));

        assertEquals(rule1.hashCode(), rule2.hashCode());
    }

    @Test
    public void testEquals() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "labor", BigDecimal.valueOf(5));
        FinancialRule rule1 = ruleFactory.buildRule("markup", "labor", BigDecimal.valueOf(5));

        assertEquals(rule1, rule2);
        assertEquals(rule2, rule1);
    }

}
