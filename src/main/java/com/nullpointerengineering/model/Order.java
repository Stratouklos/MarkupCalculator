package com.nullpointerengineering.model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Order value object
 */
public class Order {

    private final int workers;
    private final String type;
    private final BigDecimal orderValue;

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
        BigDecimal orderValue = new BigDecimal(valueString);
        return new Order(orderValue, workers, type);
    }

    private Order(BigDecimal orderValue, int workers, String type){
        this.type = type;
        this.workers = workers;
        this.orderValue = orderValue;
    }

    public int getWorkers() {
        return workers;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
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