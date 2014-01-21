package com.nullpointerengineering;

import com.nullpointerengineering.data.OrderedRuleRepository;
import com.nullpointerengineering.data.RuleRepository;
import com.nullpointerengineering.input.LineReaderFromFile;
import com.nullpointerengineering.input.RuleParser;
import com.nullpointerengineering.input.ThreeLineOrderParser;
import com.nullpointerengineering.model.*;

import java.io.IOException;
import java.util.Collection;

public class MarkupCalculator {

    public static final String NL = System.getProperty("line.separator");

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.print("Invalid number of arguments" + NL);
            printUsage();
            System.exit(-1);
        }

        @SuppressWarnings("ConstantConditions")
        String ordersFile = args[0];
        String rulesFile = args[1];

        LineReaderFromFile orderReader = new LineReaderFromFile(ordersFile);
        ThreeLineOrderParser orderParser = new ThreeLineOrderParser();

        LineReaderFromFile ruleReader = new LineReaderFromFile(rulesFile);
        FinancialRuleComparator ruleComparator = FinancialRuleComparator.first(FlatMarkupRule.class);
        RuleRepository ruleRepository =  new OrderedRuleRepository(ruleComparator);
        RuleParser ruleParser = new RuleParser(new FinancialRuleFactory(), ruleRepository);

        try {
            orderReader.read(orderParser);
            ruleReader.read(ruleParser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collection<FinancialRule> rules = ruleRepository.getRules();

        Collection<Order> orders = orderParser.getOrders();

        for (Order order : orders) {
            for (FinancialRule rule : rules) {
                rule.applyTo(order);
                System.out.println(order.getTotalValue().toString());
            }
        }

    }

    private static void printUsage() {
        System.out.print(
                "The application takes two arguments." + NL +
                "First argument is the file containing the orders." + NL +
                "Second argument is the file containing the rules to be applied.");
    }

}
