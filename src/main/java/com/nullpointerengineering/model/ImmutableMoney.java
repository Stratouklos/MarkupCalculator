package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.util.Currency;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_EVEN;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 */
public class ImmutableMoney implements Money{

    private static final BigDecimal ONE_HUNDRED = valueOf(100);
    private static final int SCALE = 2;

    private final BigDecimal value;
    private final Currency currency = Currency.getInstance("USD");

    public ImmutableMoney(String valueString) {
        this(new BigDecimal(valueString));
    }

    public ImmutableMoney(BigDecimal value) {
        this.value = value.setScale(SCALE, HALF_EVEN);
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Money applyPercentage(BigDecimal percentage) {
        return new ImmutableMoney(value.multiply(percentage).divide(ONE_HUNDRED));
    }

    @Override
    public String toString() {
        return String.format("$%(.2f", value);
    }

    @Override
    public Money add(Money moreMoney) {
        if (!this.currency.equals(moreMoney.getCurrency()))
            throw new IllegalArgumentException("Currency mismatch convert the currency first");
        return new ImmutableMoney(moreMoney.getValue().add(value));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (! (object instanceof ImmutableMoney)) return false;
        ImmutableMoney that = (ImmutableMoney) object;

        return this.value.equals(that.getValue()) && this.currency.equals(that.getCurrency());
    }

    @Override
    public int hashCode() {
        int hash = 92;
        hash = hash * 12 + currency.hashCode();
        hash = hash * 33 + value.hashCode();
        return hash;
    }

}
