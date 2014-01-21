package com.nullpointerengineering.model;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 *
 */
public interface Order {

    int getWorkers();
    String getType();
    Money getBaseValue();
    Money getTotalValue();
    void addToBaseValue(Money valueToAdd);
    void addToTotalValue(Money valueToAdd);

}
