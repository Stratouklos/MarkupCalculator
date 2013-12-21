package com.nullpointerengineering.input;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Build collections of orders
 */
public interface OrderCollectionBuilder {

    public Collection<?> build() throws Exception;

}