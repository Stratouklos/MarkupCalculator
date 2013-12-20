package com.nullpointerengineering.input;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Tests for the data reader.
 */
public class OrderCollectionBuilderFromFileTest {

    public static final String input1 = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\input1";

    private OrderCollectionBuilder dataReaderUnderTest;

    @Test
    public void testDataReader() {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile(input1);
        dataReaderUnderTest.build();
    }
}