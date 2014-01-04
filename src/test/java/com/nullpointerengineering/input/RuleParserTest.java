package com.nullpointerengineering.input;

import com.nullpointerengineering.model.FlatMarkupRule;
import com.nullpointerengineering.model.LaborMarkupRule;
import com.nullpointerengineering.model.ProductTypeMarkupRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static com.nullpointerengineering.input.RuleParser.BADLY_FORMATTED_RULE;
import static com.nullpointerengineering.input.RuleParser.NON_MARKUP_RULES_NOT_SUPPORTED;
import static com.nullpointerengineering.input.RuleParser.TOO_MANY_WORDS_AS_TYPE_DESCRIPTIONS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class RuleParserTest {

    RuleParser parser = new RuleParser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void flatMarkupTest() {
        parser.parse("flat_markup=5");

        FlatMarkupRule ruleRead = (FlatMarkupRule) parser.getRules().iterator().next();

        assertThat(ruleRead.getMarkup(), is(BigDecimal.valueOf(0.05)));
    }

    @Test
    public void workerMarkupTest() {
        parser.parse("labor_markup=5");

        LaborMarkupRule ruleRead = (LaborMarkupRule) parser.getRules().iterator().next();

        assertThat(ruleRead.getMarkup(), is(BigDecimal.valueOf(0.05)));
    }

    @Test
    public void drugsMarkupTest() {
        parser.parse("drugs_markup=5");

        ProductTypeMarkupRule ruleRead = (ProductTypeMarkupRule) parser.getRules().iterator().next();

        assertThat(ruleRead.getMarkup(), is(BigDecimal.valueOf(0.05)));
        assertThat(ruleRead.getProductType(), is("drugs"));
    }

    @Test
    public void electronicsMarkupTest() {
        parser.parse("Electronics_markup=5");

        ProductTypeMarkupRule ruleRead = (ProductTypeMarkupRule) parser.getRules().iterator().next();

        assertThat(ruleRead.getMarkup(), is(BigDecimal.valueOf(0.05)));
        assertThat(ruleRead.getProductType(), is("electronics"));
    }

    @Test
    public void testDecimalMarkupValue() {
        parser.parse("Electronics_markup=5.05");

        ProductTypeMarkupRule ruleRead = (ProductTypeMarkupRule) parser.getRules().iterator().next();

        assertThat(ruleRead.getMarkup(), is(BigDecimal.valueOf(0.0505)));
    }

    @Test
    public void testUnknownRulesThrowException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(NON_MARKUP_RULES_NOT_SUPPORTED);
        parser.parse("electronics_mark=5");
    }

    @Test
    public void testTooManyWordsDescriptionThrowsException(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(TOO_MANY_WORDS_AS_TYPE_DESCRIPTIONS);
        parser.parse("electronics_gear_markup=5");
    }

    @Test
    public void testIllegalSeparatorUsedThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(RuleParser.BADLY_FORMATTED_RULE);
        parser.parse("electronics|markup=5");
    }

    @Test
    public void testMissingValueThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(BADLY_FORMATTED_RULE);
        parser.parse("electronics_markup=");

    }

    @Test
    public void testNotUsingEqualsToSeparateLabelValueThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(BADLY_FORMATTED_RULE);
        parser.parse("electronics_markup:5");
    }


    @Test
    public void testNonNumericalValueThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(BADLY_FORMATTED_RULE);
        parser.parse("electronics_markup=five");
    }

}
