package ru.sfedu.brms.models.rules;

import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;

import java.time.Duration;
import java.util.Objects;

public class RuleByTime extends Rule {
    private Duration timeDuration;
    private float discount;

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTimeDuration(), getDiscount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleByTime)) return false;
        if (!super.equals(o)) return false;
        RuleByTime that = (RuleByTime) o;
        return Float.compare(that.getDiscount(), getDiscount()) == 0 && Objects.equals(getTimeDuration(), that.getTimeDuration());
    }

    @Override
    public String toString() {
        return "RuleByTime{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timeDuration=" + timeDuration +
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
        return RuleTypes.RULE_BY_TIME;
    }

    public Duration getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(Duration timeDuration) {
        this.timeDuration = timeDuration;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
