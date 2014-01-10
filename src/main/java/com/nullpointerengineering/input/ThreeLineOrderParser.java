package com.nullpointerengineering.input;

import com.nullpointerengineering.data.OrderRepository;
import com.nullpointerengineering.model.Order;
import com.nullpointerengineering.model.OrderImpl;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * An object to parse strings into Orders it uses internal state heavily and it's NOT thread safe
 */
public class ThreeLineOrderParser implements OrderRepository, Parser {

    private int workers = -1;
    private String money , type;

    private Collection<Order> orders = new HashSet<>();

    public static final String SET_FIELDS_ONCE_ERROR = "Each field of an order can only be set once";
    public static final String INCOMPLETE_DATA_ERROR = "Incomplete data error, please ensure all data have been processed";
    public static final String ILLEGAL_LINE_FORMAT_ERROR = "Illegal line format";

    private static final String MONEY_PATTERN = "\\$\\d+\\.\\d{2}";
    private static final String WORKERS_PATTERN = "\\d+ (people|person)";
    private static final String TYPE_PATTERN = "[a-zA-Z]*";

    /**
     * Parse a line of data to create orders.
     * @param dataLine A line of data
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    @Override
    public void parse(String dataLine){
        if (dataLine.trim().isEmpty()) return;

        if ( checkAndReadMoney(dataLine) || checkAndReadWorkers(dataLine) || checkAndReadType(dataLine) ) {
            //Something was successfully read check to see if there is enough data to complete an order
            if (orderIsReady()) {
                orders.add(OrderImpl.newOrder(money, workers, type));
                resetValues();
            }
        } else {
            //The line was not consumed by any of the parsing methods, something is wrong
            throw new IllegalArgumentException(ILLEGAL_LINE_FORMAT_ERROR);
        }
    }

    @Override
    public Collection<Order> getOrders() {
        if (moneyIsSet() || typeIsSet() || workersAreSet())
            throw new IllegalStateException(INCOMPLETE_DATA_ERROR);
        return orders;
    }

    private boolean checkAndReadMoney(String line) {
        if (line.matches(MONEY_PATTERN)) {
            if (moneyIsSet()) throw new IllegalStateException(SET_FIELDS_ONCE_ERROR);
            money = line.substring(1);
            return true;
        }
        return false;
    }

    private boolean moneyIsSet() {
        return money != null;
    }

    private boolean checkAndReadWorkers(String line) {
        if (line.matches(WORKERS_PATTERN)) {
            if (workersAreSet()) throw new IllegalStateException(SET_FIELDS_ONCE_ERROR);
            workers = Integer.parseInt(line.replaceFirst(" (people|person)", ""));
            return true;
        }
        return false;
    }

    private boolean workersAreSet() {
        return workers > 0;
    }

    private boolean checkAndReadType(String line) {
        if (line.matches(TYPE_PATTERN)) {
            if (typeIsSet()) throw new IllegalStateException(SET_FIELDS_ONCE_ERROR);
            type = line;
            return true;
        }
        return false;
    }

    private boolean typeIsSet() {
        return type != null;
    }

    private boolean orderIsReady() {
        return workersAreSet() && typeIsSet() && moneyIsSet();
    }

    private void resetValues() {
        workers = -1;
        type = null;
        money = null;
    }

}
