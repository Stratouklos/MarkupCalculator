package com.nullpointerengineering.module;

import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;
import com.nullpointerengineering.input.OrderParser;
import com.nullpointerengineering.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;
import static com.nullpointerengineering.TestResources.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Module tests for parsing orders from flat files
 */
@RunWith(JUnit4.class)
public class OrderParsingModuleTest {


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    OrderParser parser = new OrderParser();

    @Test
    public void readOneOrder() throws Exception {
        List<Order> orders = FluentIterable.from(
                Files.asCharSource(new File(ONE_ORDER), UTF_8).readLines())
                .transform(parser)
                .filter(Predicates.notNull())
                .toList();

        assertThat(orders.size(), is(1));
    }

    @Test
    public void readThreeOrders() throws Exception {
        List<Order> orders = FluentIterable.from(
                Files.asCharSource(new File(THREE_ORDERS), UTF_8).readLines())
                .transform(parser)
                .filter(Predicates.notNull())
                .toList();
        assertThat(orders.size(), is(3));
    }

    @Test
    public void readValueCorrectly() throws Exception {
        Order readOrder = FluentIterable.from(
                Files.asCharSource(new File(ONE_ORDER), UTF_8).readLines())
                .transform(parser)
                .filter(Predicates.notNull())
                .first()
                .get();

        BigDecimal expectedValue = new BigDecimal("1299.99");
        assertThat(readOrder.getTotalValue(), is(expectedValue));
    }

    @Test
    public void readFromIncompleteData() throws Exception {
        List<Order> readOrder = FluentIterable.from(
                Files.asCharSource(new File(INCOMPLETE_ORDER), UTF_8).readLines())
                .transform(parser)
                .filter(Predicates.notNull())
                .toList();

        assertThat(readOrder.size(), is(0));
    }
}
