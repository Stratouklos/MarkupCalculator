package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * A parser for orders
 */
public interface OrderParser {

    void parse(String dataLine);

    Collection<Order> buildCollection();
}
