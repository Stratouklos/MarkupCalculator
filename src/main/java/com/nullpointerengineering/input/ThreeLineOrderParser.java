package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * An object to parse strings into Orders it uses state heavily and it's NOT thread safe
 */
public class ThreeLineOrderParser implements OrderParser {

    String value;
    String workers;
    String type;

    Collection<Order> orders = new HashSet<>();

    /**
     * Parse a line of data to create orders.
     * @param dataLine A line of data
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    @Override
    public void parse(String dataLine){


    }

    @Override
    public Collection<Order> buildCollection() {
        return orders;
    }



}
