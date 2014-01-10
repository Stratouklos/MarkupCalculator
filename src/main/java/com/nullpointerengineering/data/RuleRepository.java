package com.nullpointerengineering.data;

import com.nullpointerengineering.model.FinancialRule;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Stratos
 * Holds the business rules
 */
public interface RuleRepository {

    boolean addRule(FinancialRule rule);

    Collection<FinancialRule> getRules();

}
