package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * A parser for orders
 */
public interface OrderParser {

    public static final String SET_FIELDS_ONCE_ERROR = "Each field of an order can only be set once";
    public static final String INCOMPLETE_DATA_ERROR = "Incomplete data error, please ensure all data have been processed";
    public static final String ILLEGAL_LINE_FORMAT_ERROR = "Illegal line format";

    void parse(String dataLine);

    Collection<Order> getOrders();
}
