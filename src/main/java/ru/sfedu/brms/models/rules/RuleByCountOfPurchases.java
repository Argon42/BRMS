package ru.sfedu.brms.models.rules;

import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;

public class RuleByCountOfPurchases extends RuleByCustomer{
    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_COUNT_OF_PURCHASES;
    }

    @Override
    public boolean checkRule(Check check) {
        return false;
    }

    @Override
    public boolean checkRule(Check check, Customer customer) {
        return false;
    }
}