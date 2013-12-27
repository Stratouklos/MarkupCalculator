package com.nullpointerengineering.input;

import com.nullpointerengineering.model.Order;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * An object to parse strings into Orders it uses state heavily and it's NOT thread safe
 */
public class ThreeLineOrderParser implements OrderParser {

    private int workers = -1;
    private String money , type;

    private static final String MONEY_PATTERN = "[$,€,£]\\d+\\.\\d{2}";
    private static final String WORKERS_PATTERN = "\\d+ (people|person)";
    private static final String TYPE_PATTERN = "[a-zA-Z]*";

    Collection<Order> orders = new HashSet<>();

    /**
     * Parse a line of data to create orders.
     * @param dataLine A line of data
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    @Override
    public void parse(String dataLine){
        if (dataLine.trim().isEmpty()) return;

        if (  checkAndReadMoney(dataLine) || checkAndReadWorkers(dataLine) || checkAndReadType(dataLine) ) {
            if (orderIsReady()) {
                orders.add(Order.newOrder(money, workers, type));
                resetValues();
            }
        } else {
            throw new IllegalArgumentException(ILLEGAL_LINE_FORMAT_ERROR);
        }
    }

    @Override
    public Collection<Order> getOrders() {
        if (money != null || type != null || workers > 0)
            throw new IllegalStateException(INCOMPLETE_DATA_ERROR);

        return orders;
    }

    private boolean checkAndReadMoney(String line) {
        if (line.matches(MONEY_PATTERN)) {
            if (money != null) throw new IllegalStateException(SET_FIELDS_ONCE_ERROR);
            money = line.substring(1);
            return true;
        }
        return false;
    }

    private boolean checkAndReadWorkers(String line) {
        if (line.matches(WORKERS_PATTERN)) {
            if (workers > 0) throw new IllegalStateException(SET_FIELDS_ONCE_ERROR);
            workers = Integer.parseInt(line.replaceFirst(" (people|person)", ""));
            return true;
        }
        return false;
    }

    private boolean checkAndReadType(String line) {
        if (line.matches(TYPE_PATTERN)) {
            if (type != null) throw new IllegalStateException(SET_FIELDS_ONCE_ERROR);
            type = line;
            return true;
        }
        return false;
    }

    private void resetValues() {
        workers = -1;
        type = null;
        money = null;
    }

    private boolean orderIsReady() {
        return workers > 0 && type != null && money != null;
    }

}
