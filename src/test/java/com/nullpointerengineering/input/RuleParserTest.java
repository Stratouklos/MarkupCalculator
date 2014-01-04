package com.nullpointerengineering.input;

import com.nullpointerengineering.model.FinancialRuleFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static com.nullpointerengineering.input.RuleParser.BADLY_FORMATTED_RULE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class RuleParserTest {

    FinancialRuleFactory mockFactory;
    RuleParser parserUnderTest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        mockFactory = mock(FinancialRuleFactory.class);
        parserUnderTest = new RuleParser(mockFactory);
    }

    @Test
    public void flatMarkupTest() {
        parserUnderTest.parse("flat_markup=5");
        verify(mockFactory).buildRule("markup", "flat", BigDecimal.valueOf(5));
    }

    @Test
    public void workerMarkupTest() {
        parserUnderTest.parse("labor_markup=5");

        verify(mockFactory).buildRule("markup", "labor", BigDecimal.valueOf(5));
    }

    @Test
    public void drugsMarkupTest() {
        parserUnderTest.parse("drugs_markup=5");

        verify(mockFactory).buildRule("markup", "drugs", BigDecimal.valueOf(5));
    }

    @Test
    public void electronicsMarkupTest() {
        parserUnderTest.parse("Electronics_markup=5");

        verify(mockFactory).buildRule("markup", "electronics", BigDecimal.valueOf(5));
    }

    @Test
    public void testDecimalMarkupValue() {
        parserUnderTest.parse("Electronics_markup=5.05");

        verify(mockFactory).buildRule("markup", "electronics", BigDecimal.valueOf(5.05));
    }


    @Test
    public void testIllegalSeparatorUsedThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(RuleParser.BADLY_FORMATTED_RULE);
        parserUnderTest.parse("electronics|markup=5");
    }

    @Test
    public void testMissingValueThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(BADLY_FORMATTED_RULE);
        parserUnderTest.parse("electronics_markup=");

    }

    @Test
    public void testNotUsingEqualsToSeparateLabelValueThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(BADLY_FORMATTED_RULE);
        parserUnderTest.parse("electronics_markup:5");
    }

    @Test
    public void testNonNumericalValueThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(BADLY_FORMATTED_RULE);
        parserUnderTest.parse("electronics_markup=five");
    }

}