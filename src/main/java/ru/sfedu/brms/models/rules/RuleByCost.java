package ru.sfedu.brms.models.rules;

import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;

public class RuleByCost extends Rule{
    private float minimalCost;
    private float discount;

    public RuleByCost() {
    }

    public RuleByCost(String name,float minimalCost, float discount) {
        this.setName(name);
        this.minimalCost = minimalCost;
        this.discount = discount;
    }

    @Override
    public boolean checkRule(Check check) {
        return check.getCost() >= minimalCost;
    }

    @Override
    public boolean checkRule(Check check, Customer customer) {
        return checkRule(check);
    }

    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_COST;
    }

    public float getMinimalCost() {
        return minimalCost;
    }

    public void setMinimalCost(float minimalCost) {
        this.minimalCost = minimalCost;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
