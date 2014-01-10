package com.nullpointerengineering.model;

import org.junit.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class ProductTypeMarkupRuleTest {

    FinancialRuleFactory ruleFactory = new FinancialRuleFactory();
    FinancialRule ruleUnderTest;
    Order mockOrder = mock(OrderImpl.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testMarkupForDrugs() {
        ruleUnderTest = ruleFactory.buildRule("markup", "drugs", BigDecimal.valueOf(5));
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("drugs");

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("5.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testMarkupForElectronics() {
        ruleUnderTest = ruleFactory.buildRule("markup", "electronics", BigDecimal.valueOf(5));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("ELECTRONICS");

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("5.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testMarkupIsNotAppliedForOtherType() {
        ruleUnderTest = ruleFactory.buildRule("markup", "drugs", BigDecimal.valueOf(5));

        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("pets");

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testHashCode() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "pets", BigDecimal.ZERO);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "pets", BigDecimal.ONE);

        assertEquals(rule1.hashCode(), rule2.hashCode());
    }

    @Test
    public void testEquals() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "pets", BigDecimal.ZERO);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "pets", BigDecimal.ONE);

        assertEquals(rule1, rule2);
        assertEquals(rule2, rule1);
    }

    @Test
    public void testNotEquals() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "pets", BigDecimal.ZERO);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "drugs", BigDecimal.ONE);

        assertNotEquals(rule1, rule2);
        assertNotEquals(rule2, rule1);
    }


    @Test
    public void testNullTypeThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new ProductTypeMarkupRule(BigDecimal.valueOf(5), null);
    }

    @Test
    public void testNullMarkupThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new ProductTypeMarkupRule(null, "pets");
    }

    @Test
    public void testEmptyTypeThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        ruleUnderTest = new ProductTypeMarkupRule(BigDecimal.valueOf(5), "  ");
    }

}