package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 *
 */
@RunWith(JUnit4.class)
public class WorkersMarkupRuleTest {

    private FinancialRule ruleUnderTest;
    private Order mockOrder = mock(OrderImpl.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testOneWorker() {
        ruleUnderTest = new WorkersMarkupRule(BigDecimal.valueOf(2));
        when(mockOrder.getWorkers()).thenReturn(1);
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("2.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testTwoWorkers() {
        ruleUnderTest = new WorkersMarkupRule(BigDecimal.valueOf(2));
        when(mockOrder.getWorkers()).thenReturn(2);
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("4.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testZeroWorkers() {
        ruleUnderTest = new WorkersMarkupRule(BigDecimal.valueOf(2));
        when(mockOrder.getWorkers()).thenReturn(0);
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testNullMarkupThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new WorkersMarkupRule(null);
    }
}
