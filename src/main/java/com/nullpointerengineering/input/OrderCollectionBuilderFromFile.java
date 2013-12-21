package com.nullpointerengineering.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Read orders from file
 */
public class OrderCollectionBuilderFromFile implements OrderCollectionBuilder {

    private final String fileName;

    public OrderCollectionBuilderFromFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Collection<?> build() throws IOException {
        Collection<String> orderCollection = new HashSet<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            orderCollection.add(readOrder(fileReader));
            if (fileReader.readLine() != null) {
                orderCollection.add(readOrder(fileReader));
            }
        }
        return orderCollection;
    }

    private String readOrder(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        for (int i =0 ; i < 3; i++) {
            line = reader.readLine();
            if (line == null) {
                throw new IOException("Order parsing error: incomplete order");
            } else {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

}


