package com.nullpointerengineering.data;

import com.nullpointerengineering.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Comparator;
import java.util.Iterator;

import static java.math.BigDecimal.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class OrderedRuleRepositoryTest {

    FinancialRule rule1 = new FlatMarkupRule(ONE);
    FinancialRule rule2 = new LaborMarkupRule(ONE);
    FinancialRule rule3 = new ProductTypeMarkupRule(ONE, "drugs");
    FinancialRule rule4 = new ProductTypeMarkupRule(ONE, "electronics");

    Comparator<FinancialRule> mockComparator = mock(FinancialRuleComparator.class);
    OrderedRuleRepository repositoryUnderTest = new OrderedRuleRepository(mockComparator);

    @Before
    public void setup() {
        when(mockComparator.compare(any(FinancialRule.class), any((FinancialRule.class)))).thenReturn(1);
    }

    @Test
    public void testAddition() {
        assertTrue("The rule was not added", repositoryUnderTest.addRule(rule1));
    }

    @Test
    public void testComparatorWasCalled() {
        repositoryUnderTest.addRule(rule1);
        verify(mockComparator).compare(any(FinancialRule.class), any(FinancialRule.class));
    }

    @Test
    public void testSameRuleNotAddedTwice() {
        repositoryUnderTest.addRule(rule1);
        assertFalse("The same rule was added twice", repositoryUnderTest.addRule(rule1));
    }

    @Test
    public void testCollectionIsOrdered() {
        repositoryUnderTest.addRule(rule1);
        repositoryUnderTest.addRule(rule2);
        repositoryUnderTest.addRule(rule3);
        repositoryUnderTest.addRule(rule4);

        Iterator<FinancialRule> rules = repositoryUnderTest.getRules().iterator();

        assertThat(rules.next(), is(rule1));
        assertThat(rules.next(), is(rule2));
        assertThat(rules.next(), is(rule3));
        assertThat(rules.next(), is(rule4));
    }
}