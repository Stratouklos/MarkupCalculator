package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 *
 */
@RunWith(JUnit4.class)
public class OrderWithTrailsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newOrderTest() {
        Order order = OrderWithTrails.newOrder("12223.22", 3, "food");
        assertThat(order.getType(), is("food"));
        assertThat(order.getTotalValue(), is(valueOf(12223.22)));
        assertThat(order.getWorkers(), is(3));
    }

    @Test
    public void addBaseValueTest() {
        Order order = OrderWithTrails.newOrder("12223.22", 3, "food");
        order.addToBaseValue(new ImmutableMoney("0.78"));
        assertThat(order.getBaseValue(), is(valueOf(12224.00).setScale(2)));
    }

    @Test
    public void addToTotalValueTest() {
        Order order = OrderWithTrails.newOrder("12223.22", 3, "food");
        order.addToTotalValue(valueOf(0.78));
        order.addToBaseValue(new ImmutableMoney("1.5"));
        assertThat(order.getTotalValue(), is(valueOf(12225.50).setScale(2)));
    }

    @Test
    public void addToTotalValueManyTimesTest() {
        Order order = OrderWithTrails.newOrder("12223.22", 3, "food");
        order.addToTotalValue(valueOf(0.78));
        order.addToTotalValue(valueOf(0.22));
        order.addToTotalValue(valueOf(0.11));
        order.addToTotalValue(valueOf(800.0));

        assertThat(order.getTotalValue(), is(valueOf(13024.33).setScale(2)));
    }

    @Test
    public void addToBaseAndTotalValueManyTimesTest() {
        Order order = OrderWithTrails.newOrder("12223.22", 3, "food");
        order.addToBaseValue(new ImmutableMoney("0.78"));
        order.addToBaseValue(new ImmutableMoney("1"));
        order.addToTotalValue(valueOf(0.22));
        order.addToTotalValue(valueOf(0.11));
        order.addToTotalValue(valueOf(800.0));
        assertThat(order.getTotalValue(), is(valueOf(13025.33).setScale(2)));
        assertThat(order.getBaseValue(), is(valueOf(12225.00).setScale(2)));
    }

    @Test
    public void negativeWorkersTest() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The number of workers cannot be zero or bellow");
        OrderWithTrails.newOrder("10.00", -1, "food");
    }

    @Test
    public void zeroWorkersTest() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The number of workers cannot be zero or bellow");
        OrderWithTrails.newOrder("10.00", 0, "food");
    }

    @Test
    public void nullValue() {
        thrown.expect(NullPointerException.class);
        OrderWithTrails.newOrder(null, 1, "food");
    }

    @Test
    public void nullTypeOrder() {
        thrown.expect(NullPointerException.class);
        OrderWithTrails.newOrder("10.00", 1, null);
    }

    @Test
    public void testHashFunction() {
        Order order1 = OrderWithTrails.newOrder("12223.22", 3, "food");
        order1.addToBaseValue(new ImmutableMoney("1"));
        order1.addToTotalValue(valueOf(0.22));
        Order order2 = OrderWithTrails.newOrder("12223.22", 3, "food");
        order2.addToBaseValue(new ImmutableMoney("1"));
        order2.addToTotalValue(valueOf(0.22));

        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    public void testEqualsFunction() {
        Order order1 = OrderWithTrails.newOrder("12223.22", 1, "food");
        order1.addToBaseValue(new ImmutableMoney("3"));
        order1.addToTotalValue(valueOf(6.22));
        Order order2 = OrderWithTrails.newOrder("12223.22", 1, "food");
        order2.addToBaseValue(new ImmutableMoney("3"));
        order2.addToTotalValue(valueOf(6.22));

        assertEquals(order1, order2);
        assertEquals(order2, order1);
    }



}