package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.util.Objects;

public class RuleCustomerWithPhonePattern extends RuleByCustomer {
    @CsvBindByName
    protected String pattern;

    public RuleCustomerWithPhonePattern() {
    }

    public RuleCustomerWithPhonePattern(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pattern);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RuleCustomerWithPhonePattern that = (RuleCustomerWithPhonePattern) o;
        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public String toString() {
        return "RuleCustomerWithPhonePattern{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pattern='" + pattern + '\'' +
                '}';
    }

    @Override
    public boolean checkRule(Check check) {
        return false;
    }

    @Override
    public boolean checkRule(Check check, Customer customer) {
        return customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty() && customer.getPhoneNumber().contains(pattern);
    }

    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_CUSTOMER_WITH_PHONE_PATTERN;
    }

    @Override
    public RuleValidateType getValidateType() {
        return RuleValidateType.CUSTOMER;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
