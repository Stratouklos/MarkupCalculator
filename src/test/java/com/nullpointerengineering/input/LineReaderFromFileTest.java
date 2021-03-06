package com.nullpointerengineering.input;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.VerificationMode;

import java.io.FileNotFoundException;

import static com.nullpointerengineering.TestResources.ONE_ORDER;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Tests for the data reader.
 */
@RunWith(JUnit4.class)
public class LineReaderFromFileTest {

    private static final VerificationMode ONCE = VerificationModeFactory.times(1);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    Parser mockParser = Mockito.mock(Parser.class);

    @Test
    public void readOneOrder() throws Exception {
        LineReaderFromFile lineReader = new LineReaderFromFile(ONE_ORDER);
        lineReader.read(mockParser);
        verify(mockParser, ONCE).parse("$1299.99");
        verify(mockParser, ONCE).parse("3 people");
        verify(mockParser, ONCE).parse("food");
    }

    @Test
    public void fileNotFoundTest() throws Exception {
        LineReaderFromFile dataReaderUnderTest = new LineReaderFromFile("unknownFile");
        expectedException.expect(FileNotFoundException.class);
        dataReaderUnderTest.read(mockParser);
    }

}