package com.nullpointerengineering.data;

import com.nullpointerengineering.model.FinancialRule;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Holds collection of rules in the order they should be applied.
 */
public class OrderedRuleRepository implements RuleRepository{

    private final SortedSet<FinancialRule> sortedRulesSet;

    public OrderedRuleRepository(Comparator<FinancialRule> comparator) {
        sortedRulesSet = new TreeSet<>(comparator);
    }

    @Override
    public boolean addRule(FinancialRule rule) {
        return !contains(rule) && sortedRulesSet.add(rule);
    }

    //TreeSets use compares to test for equality in our case the domain design does not confirm to the java spec
    private boolean contains(FinancialRule rule) {
        for (FinancialRule financialRule : sortedRulesSet) {
            if (financialRule.isTypeOf(rule)) return true;
        }
        return false;
    }

    /**
     * Get a sorted set of the financial rules.
     * @return SortedSet of {@link com.nullpointerengineering.model.FinancialRule}
     */
    @Override
    public SortedSet<FinancialRule> getRules() {
        return sortedRulesSet;
    }

}