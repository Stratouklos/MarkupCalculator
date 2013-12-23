package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;

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
    public Collection<? extends Order> build() throws IOException {
        Collection<Order> orderCollection = new HashSet<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            orderCollection.add(bindOrder(readThreeLinesOrThrowException(fileReader)));
            if (fileReader.readLine() != null) {
                orderCollection.add(bindOrder(readThreeLinesOrThrowException(fileReader)));
            }
        }
        return orderCollection;
    }

    private Order bindOrder(String[] lines) throws IOException {
        String orderValue = lines[0].substring(1);
        int workers =  Integer.valueOf(lines[1].replaceAll(" person| people", ""));
        String type = lines[2];
        return Order.newOrder(orderValue, workers, type);
    }

    private String[] readThreeLinesOrThrowException(BufferedReader reader) throws IOException {
        String[] lines = new String[3];
        for (int i = 0; i < 3 ; i++) {
            String line = reader.readLine();
            if (line == null) throw new IOException("Order parsing error: incomplete order");
            lines[i] = line;
        }
        return lines;
    }
}


