package com.nullpointerengineering.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Currency;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
@RunWith(JUnit4.class)
public class MoneyTest {

    Money moneyUnderTest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void stringConstructorTest() {
        moneyUnderTest = new ImmutableMoney("10");
        assertThat(moneyUnderTest.getValue(), is(valueOf(10).setScale(2)));
    }


    @Test
    public void negativeStringConstructorTest() {
        moneyUnderTest = new ImmutableMoney("-10");
        assertThat(moneyUnderTest.getValue(), is(valueOf(-10).setScale(2)));
    }

    @Test
    public void integerStringValueScaledProperly() {
        moneyUnderTest = new ImmutableMoney("10");
        assertThat(moneyUnderTest.getValue().scale(), is(2));
    }

    @Test
    public void decimalStringValueScaledProperly() {
        moneyUnderTest = new ImmutableMoney("10.666");
        assertThat(moneyUnderTest.getValue().scale(), is(2));
    }

    @Test
    public void bigDecimalValueScaledProperly() {
        moneyUnderTest = new ImmutableMoney(valueOf(34.7665));
        assertThat(moneyUnderTest.getValue().scale(), is(2));
    }

    @Test
    public void addTest() {
        moneyUnderTest = new ImmutableMoney("10");
        Money expectedMoney = moneyUnderTest.add(new ImmutableMoney("5.25"));
        assertThat(expectedMoney.toString(), is("$15.25"));
    }

    @Test
    public void addNegativeTest() {
        moneyUnderTest = new ImmutableMoney("10.76");
        Money expectedMoney = moneyUnderTest.add(new ImmutableMoney("-25.25"));
        assertThat(expectedMoney.toString(), is("$(14.49)"));
    }

    @Test
    public void applyPercentageTest(){
        moneyUnderTest = new ImmutableMoney("10");
        Money expectedMoney = moneyUnderTest.applyPercentage(valueOf(10));
        assertThat(expectedMoney.toString(), is("$1.00"));
    }

    @Test
    public void applyNegativePercentageTest(){
        moneyUnderTest = new ImmutableMoney("10");
        Money expectedMoney = moneyUnderTest.applyPercentage(valueOf(-10));
        assertThat(expectedMoney.toString(), is("$(1.00)"));
    }

    @Test
    public void positiveToStringTest() {
        moneyUnderTest = new ImmutableMoney(valueOf(3.14));
        assertThat(moneyUnderTest.toString(), is("$3.14"));
    }

    @Test
    public void negativeToStringTest() {
        moneyUnderTest = new ImmutableMoney(valueOf(-3.14));
        assertThat(moneyUnderTest.toString(), is("$(3.14)"));
    }

    @Test
    public void currencyMismatch() {
        moneyUnderTest = new ImmutableMoney(valueOf(3.14));
        Money euro = mock(Money.class);
        when(euro.getCurrency()).thenReturn(Currency.getInstance("EUR"));

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Currency mismatch");
        moneyUnderTest.add(euro);
    }

    @Test
    public void testHashCode() {
        Money money1 = new ImmutableMoney("100");
        Money money2 = new ImmutableMoney("100");

        assertEquals(money1.hashCode(), money2.hashCode());
    }

    @Test
    public void testEquals() {
        Money money1 = new ImmutableMoney("100");
        Money money2 = new ImmutableMoney("100");

        assertEquals(money1, money2);
    }

    @Test
    public void testNotEquals() {
        Money money1 = new ImmutableMoney("101");
        Money money2 = new ImmutableMoney("100");

        assertNotEquals(money1, money2);
    }
}