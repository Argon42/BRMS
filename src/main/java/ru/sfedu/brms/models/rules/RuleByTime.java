package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.InstantConverter;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.time.Instant;
import java.util.Objects;

public class RuleByTime extends Rule {
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant startTime;
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant endTime;
    @CsvBindByName
    protected float discount;

    public RuleByTime() {
    }

    public RuleByTime(String name, Instant start, Instant end, float discount) {
        this.name = name;
        this.startTime = start;
        this.endTime = end;
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startTime, endTime, discount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RuleByTime that = (RuleByTime) o;
        return Float.compare(that.discount, discount) == 0 && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public String toString() {
        return "RuleByTime{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", retailId=" + retailId +
                ", description='" + description + '\'' +
                ", retail=" + retail +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
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

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
