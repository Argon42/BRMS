package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.util.Objects;

public class RuleByCost extends Rule {

    @CsvBindByName
    protected float minimalCost;

    @CsvBindByName
    protected float discount;

    public RuleByCost() {
    }

    public RuleByCost(String name, float minimalCost, float discount) {
        this.name = name;
        this.minimalCost = minimalCost;
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minimalCost, discount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RuleByCost that = (RuleByCost) o;
        return Float.compare(that.minimalCost, minimalCost) == 0 && Float.compare(that.discount, discount) == 0;
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

    @Override
    public RuleValidateType getValidateType() {
        return RuleValidateType.CHECK;
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
