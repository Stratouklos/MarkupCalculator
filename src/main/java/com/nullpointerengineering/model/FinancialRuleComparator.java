package com.nullpointerengineering.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Compares two financial rules ensuring the flat markup goes first
 */
public class FinancialRuleComparator implements Comparator<FinancialRule> {

    public static final String ERROR_MESSAGE = "Cannot assign different priorities to the same Rule";

    private final Map<Class<? extends FinancialRule>, Integer> rulePriorityMap = new HashMap<>();

    private FinancialRuleComparator(Class<? extends FinancialRule> ruleClass) {
        rulePriorityMap.put(ruleClass, 0);
    }

    public static FinancialRuleComparator first(Class<? extends FinancialRule> ruleClassWithHighestPriority) {
        return new FinancialRuleComparator(ruleClassWithHighestPriority);
    }

    public FinancialRuleComparator then(Class<? extends FinancialRule> ruleClass) {
        checkArgument(!rulePriorityMap.containsKey(ruleClass), ERROR_MESSAGE);
        rulePriorityMap.put(ruleClass, rulePriorityMap.size());
        return this;
    }

    @Override
    public int compare(FinancialRule rule1, FinancialRule rule2) {
        if (rule1.isTypeOf(rule2)) return 0;
        int priority1 = (rulePriorityMap.containsKey(rule1.getClass())) ?
                rulePriorityMap.get(rule1.getClass()) : rulePriorityMap.size();
        int priority2 = (rulePriorityMap.containsKey(rule2.getClass())) ?
                rulePriorityMap.get(rule2.getClass()) : rulePriorityMap.size();
        return  (priority1 == priority2) ?
                1 : priority1 - priority2;
    }

}
