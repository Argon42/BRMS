package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.InstantConverter;
import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.time.Instant;
import java.util.Objects;

public class RuleByTime extends Rule {
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant start;
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant end;
    @CsvBindByName
    protected float discount;

    public RuleByTime() {
    }

    public RuleByTime(String name, Instant start, Instant end, float discount) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), start, end, discount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RuleByTime that = (RuleByTime) o;
        return Float.compare(that.discount, discount) == 0 && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public String toString() {
        return "RuleByTime{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", discount=" + discount +
                '}';
    }

    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_TIME;
    }

    @Override
    public RuleValidateType getValidateType() {
        return RuleValidateType.CHECK;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
