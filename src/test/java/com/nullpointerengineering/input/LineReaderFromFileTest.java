package com.nullpointerengineering.input;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.FileNotFoundException;

import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Tests for the data reader.
 */
@RunWith(JUnit4.class)
public class LineReaderFromFileTest {

    public static final String ONE_ORDER = "C:\\Users\\Stratos\\code\\markupCalculator\\src\\test\\resources\\one_order";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    OrderParser mockParser = Mockito.mock(OrderParser.class);

    @Test
    public void readOneOrder() throws Exception {
        LineReaderFromFile lineReader = new LineReaderFromFile(ONE_ORDER);
        lineReader.readIntoParser(mockParser);
        verify(mockParser).parse("$1299.99");
        verify(mockParser).parse("3 people");
        verify(mockParser).parse("food");
    }


    @Test
    public void fileNotFoundTest() throws Exception {
        LineReaderFromFile dataReaderUnderTest = new LineReaderFromFile("unknownFile");
        expectedException.expect(FileNotFoundException.class);
        dataReaderUnderTest.readIntoParser(mockParser);
    }

}