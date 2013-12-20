package com.nullpointerengineering.input;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Read orders from file
 */
public class OrderCollectionBuilderFromFile implements OrderCollectionBuilder {

    public OrderCollectionBuilderFromFile(String fileName) {
    }

    public Collection<?> build() {
        return new HashSet<>();
    }


}
