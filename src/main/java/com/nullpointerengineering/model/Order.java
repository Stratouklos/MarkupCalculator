package com.nullpointerengineering.model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 *
 */
public interface Order {

    int getWorkers();
    String getType();
    BigDecimal getOrderValue();

}
