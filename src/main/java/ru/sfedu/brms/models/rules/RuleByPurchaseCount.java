package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.util.Objects;

/**
 * The type Rule by purchase count.
 */
public class RuleByPurchaseCount extends Rule {
    /**
     * The Minimal cost.
     */
    @CsvBindByName
    protected float minimalCost;
    /**
     * The Discount percent.
     */
    @CsvBindByName
    protected float discountPercent;

    /**
     * Instantiates a new Rule by purchase count.
     */
    public RuleByPurchaseCount() {
    }

    /**
     * Instantiates a new Rule by purchase count.
     *
     * @param name        the name
     * @param minimalCost the minimal cost
     * @param discount    the discount
     */
    public RuleByPurchaseCount(String name, float minimalCost, float discount) {
        this.name = name;
        this.minimalCost = minimalCost;
        this.discountPercent = discount;
    }

    @Override
    public String toString() {
        return "RuleByPurchaseCount{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", retailId=" + retailId +
                ", description='" + description + '\'' +
                ", retail=" + retail +
                ", minimalCost=" + minimalCost +
                ", discountPercent=" + discountPercent +
                '}';
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

    /**
     * Gets minimal cost.
     *
     * @return the minimal cost
     */
    public float getMinimalCost() {
        return minimalCost;
    }

    /**
     * Sets minimal cost.
     *
     * @param minimalCost the minimal cost
     */
    public void setMinimalCost(float minimalCost) {
        this.minimalCost = minimalCost;
    }

    /**
     * Gets discount percent.
     *
     * @return the discount percent
     */
    public float getDiscountPercent() {
        return discountPercent;
    }

    /**
     * Sets discount percent.
     *
     * @param discountPercent the discount percent
     */
    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }
}
