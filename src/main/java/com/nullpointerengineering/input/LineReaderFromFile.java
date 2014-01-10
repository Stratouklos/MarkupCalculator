package com.nullpointerengineering.input;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Read lines from file
 */
public class LineReaderFromFile {

    private final String fileName;

    public LineReaderFromFile(String fileName) {
        this.fileName = fileName;
    }

    public void read(Parser parser) throws IOException {
        String line;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            while ((line = fileReader.readLine()) != null) {
                parser.parse(line);
            }
        }
    }
}