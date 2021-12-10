package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;

import java.util.Objects;

public class RuleByCountOfGoods extends Rule {
    @CsvBindByName
    protected int minimalCountOfGoods;
    @CsvBindByName
    protected int discount;

    public RuleByCountOfGoods() {
    }

    public RuleByCountOfGoods(String name, int minimalCountOfGoods, int discount) {
        this.name = name;
        this.minimalCountOfGoods = minimalCountOfGoods;
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minimalCountOfGoods, discount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RuleByCountOfGoods that = (RuleByCountOfGoods) o;
        return minimalCountOfGoods == that.minimalCountOfGoods && discount == that.discount;
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
        return check.getCountOfGoods() >= minimalCountOfGoods;
    }

    @Override
    public boolean checkRule(Check check, Customer customer) {
        return checkRule(check);
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
