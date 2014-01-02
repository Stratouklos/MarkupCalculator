package com.nullpointerengineering.model;

import org.junit.Test;
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
 */
@RunWith(JUnit4.class)
public class FlatMarkupRuleTest {

    private FlatMarkupRule ruleUnderTest;
    private Order mockOrder = mock(Order.class);

    @Test
    public void testFivePercent() {
        ruleUnderTest = new FlatMarkupRule(BigDecimal.valueOf(5));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("5.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testZeroPercent() {
        ruleUnderTest = new FlatMarkupRule(BigDecimal.valueOf(0));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testMinusTenPercent() {
        ruleUnderTest = new FlatMarkupRule(BigDecimal.valueOf(-10));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("-10.00");
        assertThat(actual, is(expected));
    }

}
