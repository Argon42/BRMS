package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.util.Objects;

public class RuleByCountOfPurchases extends RuleByCustomer {
    @CsvBindByName
    protected int minimalCountOfPurchases;
    @CsvBindByName
    protected float discount;

    public RuleByCountOfPurchases() {
    }

    public RuleByCountOfPurchases(String name, int minimalCountOfPurchases, float discount) {
        this.name = name;
        this.minimalCountOfPurchases = minimalCountOfPurchases;
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minimalCountOfPurchases, discount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RuleByCountOfPurchases that = (RuleByCountOfPurchases) o;
        return minimalCountOfPurchases == that.minimalCountOfPurchases && Float.compare(that.discount, discount) == 0;
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
        return customer.getChecks().size() >= minimalCountOfPurchases;
    }

    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_COUNT_OF_PURCHASES;
    }

    @Override
    public RuleValidateType getValidateType() {
        return RuleValidateType.CUSTOMER;
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
