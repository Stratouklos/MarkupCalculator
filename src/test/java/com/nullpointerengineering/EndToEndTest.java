package com.nullpointerengineering;

import com.nullpointerengineering.data.OrderedRuleRepository;
import com.nullpointerengineering.data.RuleRepository;
import com.nullpointerengineering.input.LineReaderFromFile;
import com.nullpointerengineering.input.RuleParser;
import com.nullpointerengineering.input.ThreeLineOrderParser;
import com.nullpointerengineering.model.*;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import static com.nullpointerengineering.TestResources.FIVE_RULES_FILE;
import static com.nullpointerengineering.TestResources.ONE_ORDER;
import static com.nullpointerengineering.TestResources.THREE_ORDERS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Test the main workflow.
 */
public class EndToEndTest {

    @Test
    public void singleOrder() throws IOException {
        LineReaderFromFile orderReader = new LineReaderFromFile(ONE_ORDER);
        ThreeLineOrderParser orderParser = new ThreeLineOrderParser();
        orderReader.read(orderParser);

        Collection<Order> orders = orderParser.getOrders();
        Order order = orders.iterator().next();

        LineReaderFromFile ruleReader = new LineReaderFromFile(FIVE_RULES_FILE);
        FinancialRuleComparator ruleComparator = FinancialRuleComparator.first(FlatMarkupRule.class);
        RuleRepository ruleRepository =  new OrderedRuleRepository(ruleComparator);
        RuleParser ruleParser = new RuleParser(new FinancialRuleFactory(), ruleRepository);
        ruleReader.read(ruleParser);
        Collection<FinancialRule> rules = ruleRepository.getRules();

        ValueCalculator calculator = new ValueCalculator(rules);

        BigDecimal totalValue = calculator.calculateTotalValue(order);

        assertThat(totalValue, is(BigDecimal.valueOf(1591.58)));
    }

    @Test
    public void twoOrders() throws IOException {
        LineReaderFromFile orderReader = new LineReaderFromFile(THREE_ORDERS);
        ThreeLineOrderParser orderParser = new ThreeLineOrderParser();
        orderReader.read(orderParser);
        LineReaderFromFile ruleReader = new LineReaderFromFile(FIVE_RULES_FILE);
        FinancialRuleComparator ruleComparator = FinancialRuleComparator.first(FlatMarkupRule.class);
        RuleRepository ruleRepository =  new OrderedRuleRepository(ruleComparator);
        RuleParser ruleParser = new RuleParser(new FinancialRuleFactory(), ruleRepository);
        ruleReader.read(ruleParser);
        Collection<FinancialRule> rules = ruleRepository.getRules();
        ValueCalculator calculator = new ValueCalculator(rules);

        Iterator<Order> orders = orderParser.getOrders().iterator();

        assertThat(calculator.calculateTotalValue(orders.next()), is(BigDecimal.valueOf(1591.58)));
        assertThat(calculator.calculateTotalValue(orders.next()), is(BigDecimal.valueOf(6199.81)));
        assertThat(calculator.calculateTotalValue(orders.next()), is(BigDecimal.valueOf(13707.63)));
    }

}