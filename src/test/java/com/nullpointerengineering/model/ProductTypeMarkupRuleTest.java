package com.nullpointerengineering.model;

import org.junit.*;
import org.junit.Rule;
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
public class ProductTypeMarkupRuleTest {

    private FinancialRule ruleUnderTest;
    private Order mockOrder = mock(OrderImpl.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testMarkupForDrugs() {
        ruleUnderTest = new ProductTypeMarkupRule(BigDecimal.valueOf(5), "drugs");
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("drugs");

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("5.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testMarkupForElectronics() {
        ruleUnderTest = new ProductTypeMarkupRule(BigDecimal.valueOf(5), "electronics");
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("ELECTRONICS");

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("5.00");
        assertThat(actual, is(expected));
    }

    @Test
    public void testMarkupIsNotAppliedForOtherType() {
        ruleUnderTest = new ProductTypeMarkupRule(BigDecimal.valueOf(5), "drugs");
        when(mockOrder.getOrderValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("pets");

        BigDecimal actual = ruleUnderTest.applyTo(mockOrder);
        BigDecimal expected = new BigDecimal("0.00");
        assertThat(actual, is(expected));
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