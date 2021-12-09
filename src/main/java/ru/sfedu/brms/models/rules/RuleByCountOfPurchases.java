package ru.sfedu.brms.models.rules;

import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;

import java.util.Objects;

public class RuleByCountOfPurchases extends RuleByCustomer {
    public int minimalCountOfPurchases;
    public float discount;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMinimalCountOfPurchases(), getDiscount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleByCountOfPurchases)) return false;
        if (!super.equals(o)) return false;
        RuleByCountOfPurchases that = (RuleByCountOfPurchases) o;
        return getMinimalCountOfPurchases() == that.getMinimalCountOfPurchases() && Float.compare(that.getDiscount(), getDiscount()) == 0;
    }

    @Override
    public String toString() {
        return "RuleByCountOfPurchases{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minimalCountOfPurchases=" + minimalCountOfPurchases +
                ", discount=" + discount +
                '}';
    }

    @Override
    public boolean checkRule(Check check) {
        return false;
    }

    @Override
    public boolean checkRule(Check check, Customer customer) {
        return false;
    }

    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_COUNT_OF_PURCHASES;
    }

    public int getMinimalCountOfPurchases() {
        return minimalCountOfPurchases;
    }

    public void setMinimalCountOfPurchases(int minimalCountOfPurchases) {
        this.minimalCountOfPurchases = minimalCountOfPurchases;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
