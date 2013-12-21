package com.nullpointerengineering.input;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Tests for the data reader.
 */
public class OrderCollectionBuilderFromFileTest {

    public static final String input1 = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\input1";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void readFromFileTest() throws IOException {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile(input1);
        assertThat(dataReaderUnderTest.build(), instanceOf(Collection.class));
    }

    @Test
    public void fileNotFoundTest() throws IOException {
        OrderCollectionBuilderFromFile dataReaderUnderTest = new OrderCollectionBuilderFromFile("C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\bad_input");
        expectedException.expect(FileNotFoundException.class);
        dataReaderUnderTest.build();
    }
}