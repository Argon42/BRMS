package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.util.Objects;

public class RuleByPurchaseCount extends Rule {
    @CsvBindByName
    protected float minimalCost;
    @CsvBindByName
    protected float discountPercent;

    public RuleByPurchaseCount() {
    }

    public RuleByPurchaseCount(String name, float minimalCost, float discount) {
        this.name = name;
        this.minimalCost = minimalCost;
        this.discountPercent = discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minimalCost, discountPercent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RuleByPurchaseCount that = (RuleByPurchaseCount) o;
        return Float.compare(that.minimalCost, minimalCost) == 0 && Float.compare(that.discountPercent, discountPercent) == 0;
    }

    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_PURCHASE_COUNT;
    }

    @Override
    public RuleValidateType getValidateType() {
        return RuleValidateType.CUSTOMER;
    }

    public float getMinimalCost() {
        return minimalCost;
    }

    public void setMinimalCost(float minimalCost) {
        this.minimalCost = minimalCost;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }
}
