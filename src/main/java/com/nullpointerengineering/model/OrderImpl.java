package com.nullpointerengineering.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Order value object
 */
public class OrderImpl implements Order {

    private BigDecimal baseValue;
    private final int workers;
    private final String type;
    private final Collection<BigDecimal> adjustments = new LinkedList<>();

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
        BigDecimal orderValue = new BigDecimal(valueString);
        return new OrderImpl(orderValue, workers, checkNotNull(type, "Order type cannot be null"));
    }

    private OrderImpl(BigDecimal baseValue, int workers, String type){
        this.type = type;
        this.workers = workers;
        this.baseValue = baseValue;
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
        BigDecimal totalValue = BigDecimal.ZERO.add(baseValue);
        for (BigDecimal adjustment : adjustments) {
            totalValue = totalValue.add(adjustment);
        }
        return totalValue;
    }

    @Override
    public BigDecimal getBaseValue() {
        return baseValue;
    }

    @Override
    public void addToBaseValue(BigDecimal valueToAdd) {
        baseValue = baseValue.add(valueToAdd);
    }

    @Override
    public void addToTotalValue(BigDecimal valueToAdd) {
        adjustments.add(valueToAdd);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (! (object instanceof OrderImpl)) return false;
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
        hash = hash * 19 + type.hashCode();
        return hash;
    }
}