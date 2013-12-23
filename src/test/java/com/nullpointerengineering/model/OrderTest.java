package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 *
 */
@RunWith(JUnit4.class)
public class OrderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newOrderTest() {
        Order order = Order.newOrder("12223.22", 3, "food");
        assertThat(order.getType(), is("food"));
    }

    @Test
    public void negativeWorkersTest() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The number of workers cannot be zero or bellow");
        Order.newOrder("10.00", -1, "food");
    }

    @Test
    public void zeroWorkersTest() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The number of workers cannot be zero or bellow");
        Order.newOrder("10.00", 0, "food");
    }

    @Test
    public void nullValue() {
        thrown.expect(NullPointerException.class);
        Order.newOrder(null, 1, "food");
    }

    @Test
    public void nullTypeOrder() {
        thrown.expect(NullPointerException.class);
        Order.newOrder("10.00", 1, null);
    }

    @Test
    public void testHashFunction() {
        Order order1 = Order.newOrder("12223.22", 3, "food");
        Order order2 = Order.newOrder("12223.22", 3, "food");

        assertEquals(order1.hashCode(), order2.hashCode());
    }

}
