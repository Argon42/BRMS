package ru.sfedu.brms.models.rules;

import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;

import java.util.Objects;

public class RuleByCountOfGoods extends Rule {

    private int minimalCountOfGoods;
    private int discount;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMinimalCountOfGoods(), getDiscount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleByCountOfGoods)) return false;
        if (!super.equals(o)) return false;
        RuleByCountOfGoods that = (RuleByCountOfGoods) o;
        return getMinimalCountOfGoods() == that.getMinimalCountOfGoods() && getDiscount() == that.getDiscount();
    }

    @Override
    public String toString() {
        return "RuleByCountOfGoods{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", minimalCountOfGoods=" + minimalCountOfGoods +
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
        return RuleTypes.RULE_BY_COUNT_OF_GOODS;
    }

    public int getMinimalCountOfGoods() {
        return minimalCountOfGoods;
    }

    public void setMinimalCountOfGoods(int minimalCountOfGoods) {
        this.minimalCountOfGoods = minimalCountOfGoods;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
