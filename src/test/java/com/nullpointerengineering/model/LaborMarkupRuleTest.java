package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 *
 */
@RunWith(JUnit4.class)
public class LaborMarkupRuleTest {

    FinancialRuleFactory ruleFactory = new FinancialRuleFactory();
    FinancialRule ruleUnderTest;
    Order mockOrder = mock(OrderWithTrails.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testOneWorker() {
        ruleUnderTest = ruleFactory.buildRule("markup", "labor", valueOf(2));

        when(mockOrder.getWorkers()).thenReturn(1);
        when(mockOrder.getBaseValue()).thenReturn(valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToTotalValue(new ImmutableMoney("2"));
    }

    @Test
    public void testTwoWorkers() {
        ruleUnderTest = ruleFactory.buildRule("markup", "labor", valueOf(2));

        when(mockOrder.getWorkers()).thenReturn(2);
        when(mockOrder.getBaseValue()).thenReturn(valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToTotalValue(new ImmutableMoney("4"));
    }

    @Test
    public void testZeroWorkers() {
        ruleUnderTest = ruleFactory.buildRule("markup", "labor", valueOf(2));

        when(mockOrder.getWorkers()).thenReturn(0);
        when(mockOrder.getBaseValue()).thenReturn(valueOf(100.01));

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToTotalValue(new ImmutableMoney(ZERO));
    }

    @Test
    public void testTypeOf() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "labor", ONE);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "labor", ZERO);

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
        FinancialRule rule1 = ruleFactory.buildRule("markup", "labor", valueOf(5));
        FinancialRule rule2 = ruleFactory.buildRule("markup", "labor", valueOf(5));

        assertEquals(rule1.hashCode(), rule2.hashCode());
    }

    @Test
    public void testEquals() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "labor", valueOf(5));
        FinancialRule rule1 = ruleFactory.buildRule("markup", "labor", valueOf(5));

        assertEquals(rule1, rule2);
        assertEquals(rule2, rule1);
    }

    @Test
    public void testToString() {
        FinancialRule rule = ruleFactory.buildRule("markup", "labor", valueOf(5));

        assertThat(rule.toString(), is("Labor markup rule of 5.00 percent"));
    }

}