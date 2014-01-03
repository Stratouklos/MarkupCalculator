package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;
import com.nullpointerengineering.model.OrderImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;

import static com.nullpointerengineering.input.OrderParser.INCOMPLETE_DATA_ERROR;
import static com.nullpointerengineering.input.OrderParser.SET_FIELDS_ONCE_ERROR;
import static com.nullpointerengineering.input.OrderParser.ILLEGAL_LINE_FORMAT_ERROR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class OrderParserTest {

    private ThreeLineOrderParser orderParserUnderTest = new ThreeLineOrderParser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parseOneOrderTest() {
        orderParserUnderTest.parse("$10.10");
        orderParserUnderTest.parse("3 people");
        orderParserUnderTest.parse("food");

        Order expectedOrder = OrderImpl.newOrder("10.10", 3, "food");
        Order actualOrder = orderParserUnderTest.getOrders().iterator().next();

        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void parseSkipBlanksTest() {
        orderParserUnderTest.parse("$10.10");
        orderParserUnderTest.parse("");
        orderParserUnderTest.parse("3 people");
        orderParserUnderTest.parse(" ");
        orderParserUnderTest.parse("food");

        Order expectedOrder = OrderImpl.newOrder("10.10", 3, "food");
        Order actualOrder = orderParserUnderTest.getOrders().iterator().next();

        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void parseTwoOrdersTest() {
        orderParserUnderTest.parse("$10.10");
        orderParserUnderTest.parse("3 people");
        orderParserUnderTest.parse("food");
        orderParserUnderTest.parse("$20.22");
        orderParserUnderTest.parse("1 person");
        orderParserUnderTest.parse("drugs");

        Order expectedOrder = OrderImpl.newOrder("20.22", 1, "drugs");
        Iterator<Order> orderIterator = orderParserUnderTest.getOrders().iterator();
        orderIterator.next();
        Order actualOrder = orderParserUnderTest.getOrders().iterator().next();

        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void readMoneyTwice() {
        orderParserUnderTest.parse("$4455.00");

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(SET_FIELDS_ONCE_ERROR);
        orderParserUnderTest.parse("$6455.00");
    }

    @Test
    public void readWorkersTwice() {
        orderParserUnderTest.parse("1 person");

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(SET_FIELDS_ONCE_ERROR);
        orderParserUnderTest.parse("2 people");
    }

    @Test
    public void readTypeTwice() {
        orderParserUnderTest.parse("soda");

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(SET_FIELDS_ONCE_ERROR);
        orderParserUnderTest.parse("drugs");
    }

    @Test
    public void parseIncompleteOrderTest() {
        orderParserUnderTest.parse("$10.10");
        orderParserUnderTest.parse("3 people");

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(INCOMPLETE_DATA_ERROR);
        orderParserUnderTest.getOrders();
    }

    @Test
    public void illegalLineFormatTest() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(ILLEGAL_LINE_FORMAT_ERROR);
        orderParserUnderTest.parse("$1000.007");
    }

}
