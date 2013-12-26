package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Module tests for parsing orders from flat files
 */
@RunWith(JUnit4.class)
public class OrderParsingModuleTest {

    public static final String ONE_ORDER = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\one_order";
    public static final String TWO_ORDERS = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\two_orders";
    public static final String INCOMPLETE_ORDER = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\incomplete_order";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    OrderParser parser = new ThreeLineOrderParser();
    LineReaderFromFile lineReaderFromFile;

    @Test
    @Ignore
    public void readOneOrder() throws Exception {
        lineReaderFromFile = new LineReaderFromFile(ONE_ORDER);
        lineReaderFromFile.readIntoParser(parser);
        Collection<? super Order> orders = parser.buildCollection();
        assertThat(orders.size(), is(1));
    }

    @Test
    @Ignore
    public void readTwoOrders() throws Exception {
        lineReaderFromFile = new LineReaderFromFile(TWO_ORDERS);
        lineReaderFromFile.readIntoParser(parser);
        Collection<? super Order> orders = parser.buildCollection();
        assertThat(orders.size(), is(2));
    }

    @Test
    @Ignore
    public void readValueCorrectly() throws Exception {
        lineReaderFromFile = new LineReaderFromFile(ONE_ORDER);
        lineReaderFromFile.readIntoParser(parser);
        Order readOrder = parser.buildCollection().iterator().next();
        BigDecimal expectedValue = new BigDecimal("1299.99");
        assertThat(readOrder.getOrderValue(), is(expectedValue));
    }

    @Test
    @Ignore
    public void readFromIncompleteData() throws Exception {
        lineReaderFromFile = new LineReaderFromFile(INCOMPLETE_ORDER);
        expectedException.expect(IOException.class);
        expectedException.expectMessage("Order parsing error: incomplete order");
        lineReaderFromFile.readIntoParser(parser);
    }
}
