package com.nullpointerengineering;

import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;
import com.nullpointerengineering.input.OrderParser;
import com.nullpointerengineering.input.RuleParser;
import com.nullpointerengineering.model.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

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

        try {
            Collection<String> outputs = FluentIterable.from(
                    Files.asCharSource(ordersFile, UTF_8).readLines())
                    .transform(new OrderParser())
                    .filter(Predicates.notNull()).transform(
                    new ValueCalculator(
                            FluentIterable.from(
                                    Files.asCharSource(rulesFile, UTF_8).readLines())
                                    .transform(new RuleParser(new FinancialRuleFactory()))
                                    .toSortedList(FinancialRuleComparator.first(FlatMarkupRule.class))
                    )
            ).toList();
            for (String output : outputs){
                System.out.println(output);
            }
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
