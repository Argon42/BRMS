package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.InstantConverter;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.time.Instant;
import java.util.Objects;

/**
 * The type Rule by time.
 */
public class RuleByTime extends Rule {
    /**
     * The Start time.
     */
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant startTime;
    /**
     * The End time.
     */
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant endTime;
    /**
     * The Discount.
     */
    @CsvBindByName
    protected float discount;

    /**
     * Instantiates a new Rule by time.
     */
    public RuleByTime() {
    }

    /**
     * Instantiates a new Rule by time.
     *
     * @param name     the name
     * @param start    the start
     * @param end      the end
     * @param discount the discount
     */
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

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Instant getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public float getDiscount() {
        return discount;
    }

    /**
     * Sets discount.
     *
     * @param discount the discount
     */
    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
