package com.nullpointerengineering;

import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;
import com.nullpointerengineering.input.OrderParser;
import com.nullpointerengineering.input.RuleParser;
import com.nullpointerengineering.model.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static com.google.common.base.Charsets.UTF_8;
import static com.nullpointerengineering.TestResources.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Test the main workflow.
 */
public class EndToEndTest {

    @Test
    public void singleOrder() throws IOException {
        Iterator<String> outputs = FluentIterable.from(
            Files.asCharSource(new File(ONE_ORDER), UTF_8).readLines())
            .transform(new OrderParser())
            .filter(Predicates.notNull()).transform(
                new ValueCalculator(
                    FluentIterable.from(
                        Files.asCharSource(new File(FIVE_RULES_FILE), UTF_8).readLines())
                        .transform(new RuleParser(new FinancialRuleFactory()))
                        .toSortedList(FinancialRuleComparator.first(FlatMarkupRule.class))
                )
            )
            .iterator();

        assertThat(outputs.next(), is("$1591.58"));
    }

    @Test
    public void threeOrders() throws IOException {
        Iterator<String> outputs = FluentIterable.from(
            Files.asCharSource(new File(THREE_ORDERS), UTF_8).readLines())
            .transform(new OrderParser())
            .filter(Predicates.notNull()).transform(
                new ValueCalculator(
                    FluentIterable.from(
                        Files.asCharSource(new File(FIVE_RULES_FILE), UTF_8).readLines())
                        .transform(new RuleParser(new FinancialRuleFactory()))
                        .toSortedList(FinancialRuleComparator.first(FlatMarkupRule.class))
                )
            )
            .iterator();

        assertThat(outputs.next(), is("$1591.58"));
        assertThat(outputs.next(), is("$6199.81"));
        assertThat(outputs.next(), is("$13707.63"));
    }

}