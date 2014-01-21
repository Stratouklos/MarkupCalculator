package com.nullpointerengineering.model;

import org.junit.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Matchers;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class ProductTypeMarkupRuleTest {

    FinancialRuleFactory ruleFactory = new FinancialRuleFactory();
    FinancialRule ruleUnderTest;
    Order mockOrder = mock(OrderWithTrails.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testMarkupForDrugs() {
        ruleUnderTest = ruleFactory.buildRule("markup", "drugs", ONE);
        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("drugs");

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToTotalValue(new ImmutableMoney(ONE));
    }

    @Test
    public void testMarkupForElectronics() {
        ruleUnderTest = ruleFactory.buildRule("markup", "electronics", ONE);

        when(mockOrder.getBaseValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("ELECTRONICS");

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder).addToTotalValue(new ImmutableMoney(ONE));
    }

    @Test
    public void testMarkupIsNotAppliedForOtherType() {
        ruleUnderTest = ruleFactory.buildRule("markup", "drugs", ONE);

        when(mockOrder.getTotalValue()).thenReturn(BigDecimal.valueOf(100.01));
        when(mockOrder.getType()).thenReturn("pets");

        ruleUnderTest.applyTo(mockOrder);

        verify(mockOrder, never()).addToTotalValue(Matchers.any(Money.class));
    }

    @Test
    public void testTypeOf() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "pets", ZERO);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "pets", ONE);

        assertTrue(rule1.isTypeOf(rule2));
        assertTrue(rule2.isTypeOf(rule1));    
    }

    @Test
    public void testNotEquals() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "pets", ZERO);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "drugs", ONE);

        assertNotEquals(rule1, rule2);
        assertNotEquals(rule2, rule1);
    }

    @Test
    public void testHashCode() {
        FinancialRule rule1 = ruleFactory.buildRule("markup", "pets", ONE);
        FinancialRule rule2 = ruleFactory.buildRule("markup", "pets", ONE);

        assertEquals(rule1.hashCode(), rule2.hashCode());
    }

    @Test
    public void testEquals() {
        FinancialRule rule2 = ruleFactory.buildRule("markup", "pets", ONE);
        FinancialRule rule1 = ruleFactory.buildRule("markup", "pets", ONE);

        assertEquals(rule1, rule2);
        assertEquals(rule2, rule1);
    }
    @Test
    public void testNullTypeThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new ProductTypeMarkupRule(ONE, null);
    }

    @Test
    public void testNullMarkupThrowsException() {
        expectedException.expect(NullPointerException.class);
        ruleUnderTest = new ProductTypeMarkupRule(null, "pets");
    }

    @Test
    public void testEmptyTypeThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        ruleUnderTest = new ProductTypeMarkupRule(ONE, "  ");
    }

    @Test
    public void testToString() {
        FinancialRule rule = ruleFactory.buildRule("markup", "pets", ONE);

        assertThat(rule.toString(), is("Product type markup rule of 1.00 percent for pets"));
    }
}