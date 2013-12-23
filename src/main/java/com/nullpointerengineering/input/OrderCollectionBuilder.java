package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Build collections of orders
 */
public interface OrderCollectionBuilder {

    public Collection<? extends Order> build() throws Exception;

}