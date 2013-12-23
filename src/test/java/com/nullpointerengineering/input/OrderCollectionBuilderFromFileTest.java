package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Tests for the data reader.
 */
@RunWith(JUnit4.class)
public class OrderCollectionBuilderFromFileTest {

    public static final String ONE_ORDER = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\one_order";
    public static final String TWO_ORDERS = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\two_orders";
    public static final String INCOMPLETE_ORDER = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\incomplete_order";
    public static final String FILE_NOT_FOUND = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\not_found";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void readOneOrder() throws Exception {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile(ONE_ORDER);
        assertThat(dataReaderUnderTest.build().size(), is(1));
    }

    @Test
    public void readTwoOrders() throws Exception {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile(TWO_ORDERS);
        assertThat(dataReaderUnderTest.build().size(), is(2));
    }

    @Test
    public void readValueCorrectly() throws Exception {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile(ONE_ORDER);
        Order readOrder = dataReaderUnderTest.build().iterator().next();
        BigDecimal expectedValue = new BigDecimal("1299.99");
        assertThat(readOrder.getOrderValue(), is(expectedValue));
    }

    @Test
    public void fileNotFoundTest() throws Exception {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile(FILE_NOT_FOUND);
        expectedException.expect(FileNotFoundException.class);
        dataReaderUnderTest.build();
    }

    @Test
    public void readFromIncompleteData() throws Exception {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile(INCOMPLETE_ORDER);
        expectedException.expect(IOException.class);
        expectedException.expectMessage("Order parsing error: incomplete order");
        dataReaderUnderTest.build();
    }

}