package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * A repository for orders
 */
public interface OrderRepository {

    Collection<Order> getOrders();
}
