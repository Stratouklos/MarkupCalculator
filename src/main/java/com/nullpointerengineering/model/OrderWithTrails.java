package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

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
        if (workers <= 0 ) throw new IllegalArgumentException("The number of workers cannot be zero or bellow");
        if (type == null) throw new NullPointerException("Order type cannot be null");
        Money orderValue = new ImmutableMoney(valueString);
        return new OrderWithTrails(orderValue, workers, type);
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
    public BigDecimal getTotalValue() {
        return getBaseValue().add(addUp(adjustments));
    }

    @Override
    public BigDecimal getBaseValue() {
        return addUp(baseValueAdjustments);
    }

    @Override
    public void addToBaseValue(Money moneyToAdd) {
        baseValueAdjustments.add(moneyToAdd);
    }

    @Override
    public void addToTotalValue(BigDecimal valueToAdd) {
        adjustments.add(new ImmutableMoney(valueToAdd));
    }

    private BigDecimal addUp(Collection<Money> moneys) {
        Money total = new ImmutableMoney("0");
        for (Money money : moneys) {
            total = total.add(money);
        }
        return total.getValue();
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
        int hash = 1;
        hash = hash * 42 + workers;
        hash = hash * 22 + type.hashCode();
        hash = hash * 21 + getBaseValue().hashCode();
        hash = hash * 19 + getTotalValue().hashCode();
        return hash;
    }
}