package com.nullpointerengineering.model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Financial rules that can be applied to an order to generate a price adjustment
 */
public interface Rule {

    BigDecimal applyTo(Order order);

}
