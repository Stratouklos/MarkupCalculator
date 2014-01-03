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
 */
@RunWith(JUnit4.class)
public class FlatMarkupRuleTest {

    private FlatMarkupRule ruleUnderTest;
    private Order mockOrder = mock(OrderImpl.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @Test
    public void testOneHundredAndTenPercent() {
        ruleUnderTest = new FlatMarkupRule(BigDecimal.valueOf(110));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("110.01");
        assertThat(actual, is(expected));
    }

    @Test
    public void testThreeThousandPercent() {
        ruleUnderTest = new FlatMarkupRule(BigDecimal.valueOf(3000));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("3000.30");
        assertThat(actual, is(expected));
    }

    @Test
    public void testPointZeroOnePercent() {
        ruleUnderTest = new FlatMarkupRule(BigDecimal.valueOf(0.01));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.01");
        assertThat(actual, is(expected));
    }

    @Test
    public void testPointZeroZeroZeroOnePercent() {
        ruleUnderTest = new FlatMarkupRule(BigDecimal.valueOf(0.0001));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testNullMarkupThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new FlatMarkupRule(null);
    }


}
