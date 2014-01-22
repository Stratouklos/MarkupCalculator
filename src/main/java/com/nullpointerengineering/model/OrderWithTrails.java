package com.nullpointerengineering.model;

import com.google.common.hash.Hashing;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Order value object
 */
public class OrderWithTrails implements Order {

    private final int workers;
    private final String type;
    private final Collection<Money> baseValueAdjustments = new LinkedList<>();
    private final Collection<Money> adjustments = new LinkedList<>();

    /**
     * Factory method for orders
     * @param valueString the order's value in the form of a string. Ensure that the format is the same as that accepted
     *                    by  {@link BigDecimal(String)} constructor.
     * @param workers     the number of workers that will be working on the order.
     * @param type        the type of products that will be worked on e.g. food, pharmaceuticals
     * @return            a new instance of an order object
     * @throws NumberFormatException if {@code valueString} is not a valid
     *         representation of a {@code BigDecimal}.
     * @throws IllegalArgumentException if {@code workers} is zero or negative.
     */
    public static Order newOrder(String valueString, int workers, String type) {
        checkArgument(!(workers <= 0), "The number of workers cannot be zero or bellow");
        Money orderValue = new ImmutableMoney(valueString);
        return new OrderWithTrails(orderValue, workers, checkNotNull(type, "Order type cannot be null"));
    }

    private OrderWithTrails(Money baseValue, int workers, String type){
        this.type = type;
        this.workers = workers;
        baseValueAdjustments.add(baseValue);
    }

    @Override
    public int getWorkers() {
        return workers;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Money getTotalValue() {
        return getBaseValue().add(addUp(adjustments));
    }

    @Override
    public Money getBaseValue() {
        return addUp(baseValueAdjustments);
    }

    @Override
    public void addToBaseValue(Money moneyToAdd) {
        baseValueAdjustments.add(moneyToAdd);
    }

    @Override
    public void addToTotalValue(Money valueToAdd) {
        adjustments.add(valueToAdd);
    }

    private Money addUp(Collection<Money> moneys) {
        Money total = new ImmutableMoney("0");
        for (Money money : moneys) {
            total = total.add(money);
        }
        return total;
    }

    //Equals and hashcode implemented for testing reasons not necessary to be too strict
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (! (object instanceof OrderWithTrails)) return false;
        Order that = (Order) object;

        return this.getType().equals(that.getType()) &&
               this.getWorkers() == that.getWorkers() &&
               this.getBaseValue().equals(that.getBaseValue()) &&
               this.getTotalValue().equals(that.getTotalValue());
    }

    @Override
    public int hashCode() {
        return Hashing.md5()
            .newHasher()
            .putInt(workers)
            .putString(type, UTF_8)
            .putString(getTotalValue().toString(), UTF_8)
            .putString(getBaseValue().toString(), UTF_8)
            .hash()
            .asInt();
    }
}