package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;
import com.nullpointerengineering.model.OrderImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

    private OrderParser orderParserUnderTest = new OrderParser();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parseOneOrderTest() {
        orderParserUnderTest.apply("$10.10");
        orderParserUnderTest.apply("3 people");
        Order actualOrder = orderParserUnderTest.apply("food");

        Order expectedOrder = OrderImpl.newOrder("10.10", 3, "food");

        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void parseSkipBlanksTest() {
        orderParserUnderTest.apply("$10.10");
        orderParserUnderTest.apply("");
        orderParserUnderTest.apply("3 people");
        orderParserUnderTest.apply(" ");
        Order actualOrder = orderParserUnderTest.apply("food");

        Order expectedOrder = OrderImpl.newOrder("10.10", 3, "food");

        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void parseTwoOrdersTest() {
        List<Order> orders = new LinkedList<>();


        orderParserUnderTest.apply("$10.10");
        orderParserUnderTest.apply("3 people");
        orders.add(orderParserUnderTest.apply("food"));
        orderParserUnderTest.apply("$20.22");
        orderParserUnderTest.apply("1 person");
        orders.add(orderParserUnderTest.apply("drugs"));

        Order expectedOrder = OrderImpl.newOrder("20.22", 1, "drugs");
        Iterator<Order> orderIterator = orders.iterator();
        orderIterator.next();
        Order actualOrder = orderIterator.next();

        assertThat(actualOrder, is(expectedOrder));
    }

    @Test
    public void readMoneyTwice() {
        orderParserUnderTest.apply("$4455.00");

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(SET_FIELDS_ONCE_ERROR);
        orderParserUnderTest.apply("$6455.00");
    }

    @Test
    public void readWorkersTwice() {
        orderParserUnderTest.apply("1 person");

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(SET_FIELDS_ONCE_ERROR);
        orderParserUnderTest.apply("2 people");
    }

    @Test
    public void readTypeTwice() {
        orderParserUnderTest.apply("soda");

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage(SET_FIELDS_ONCE_ERROR);
        orderParserUnderTest.apply("drugs");
    }

    @Test
    public void illegalLineFormatTest() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(ILLEGAL_LINE_FORMAT_ERROR);
        orderParserUnderTest.apply("$1000.007");
    }

}
