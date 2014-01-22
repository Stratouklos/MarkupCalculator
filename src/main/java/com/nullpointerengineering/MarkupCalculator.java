package com.nullpointerengineering;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;
import com.nullpointerengineering.input.OrderParser;
import com.nullpointerengineering.input.RuleParser;
import com.nullpointerengineering.model.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;

import static com.google.common.base.Charsets.UTF_8;

public class MarkupCalculator {

    public static final String NL = System.getProperty("line.separator");

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.print("Invalid number of arguments" + NL);
            printUsage();
            System.exit(-1);
        }

        @SuppressWarnings("ConstantConditions")
        File ordersFile = new File(args[0]);
        File rulesFile = new File(args[1]);
        OrderParser orderParser = new OrderParser();
        RuleParser ruleParser = new RuleParser(new FinancialRuleFactory());
        Comparator<FinancialRule> flatMarkupFirst = FinancialRuleComparator.first(FlatMarkupRule.class);
        Joiner newLineJoiner = Joiner.on(NL);
        Function<Order,String> totalValueReader =
            new Function<Order, String>() {
                @Override
                public String apply(Order order) {
                    return order.getPrintableTotalValue();
                }
            };

        try {
            Collection<FinancialRule> rules = FluentIterable.from(Files.asCharSource(rulesFile, UTF_8).readLines())
                    .transform(ruleParser)
                    .toSortedList(flatMarkupFirst);

            Collection<String> values = FluentIterable.from(Files.asCharSource(ordersFile, UTF_8).readLines())
                    .transform(orderParser)
                    .filter(Predicates.notNull())
                    .transform( new ValueCalculator( rules))
                    .transform(totalValueReader)
                    .toList();

            System.out.print( newLineJoiner.join(values));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.print(
                "The application takes two arguments." + NL +
                "First argument is the file containing the orders." + NL +
                "Second argument is the file containing the rules to be applied.");
    }

}
