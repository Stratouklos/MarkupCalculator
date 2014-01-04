package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.nullpointerengineering.model.FinancialRuleFactory.UNSUPPORTED_TYPE;
import static java.math.BigDecimal.ONE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class FinancialRuleFactoryTest {

    FinancialRuleFactory factoryUnderTest = new FinancialRuleFactory();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFlatMarkupRule() {
        FinancialRule rule = factoryUnderTest.buildRule("markup", "flat", ONE);

        assertThat(rule, instanceOf(FlatMarkupRule.class));
    }

    @Test
    public void testLaborMarkupRule() {
        FinancialRule rule = factoryUnderTest.buildRule("markup", "labor", ONE);

        assertThat(rule, instanceOf(LaborMarkupRule.class));
    }

    @Test
    public void testUnknownRulesThrowException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(UNSUPPORTED_TYPE);
        factoryUnderTest.buildRule("discount", "flat", ONE);
    }

}
