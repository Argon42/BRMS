package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.util.Objects;

/**
 * The type Rule by count of goods.
 */
public class RuleByCountOfGoods extends Rule {
    /**
     * The Minimal count of goods.
     */
    @CsvBindByName
    protected int minimalCountOfGoods;
    /**
     * The Discount.
     */
    @CsvBindByName
    protected int discount;

    /**
     * Instantiates a new Rule by count of goods.
     */
    public RuleByCountOfGoods() {
    }

    /**
     * Instantiates a new Rule by count of goods.
     *
     * @param name                the name
     * @param minimalCountOfGoods the minimal count of goods
     * @param discount            the discount
     */
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
                ", retailId=" + retailId +
                ", description='" + description + '\'' +
                ", retail=" + retail +
                ", minimalCountOfGoods=" + minimalCountOfGoods +
                ", discount=" + discount +
                '}';
    }

    @Override
    public RuleTypes getRuleType() {
        return RuleTypes.RULE_BY_COUNT_OF_GOODS;
    }

    @Override
    public RuleValidateType getValidateType() {
        return RuleValidateType.CHECK;
    }

    /**
     * Gets minimal count of goods.
     *
     * @return the minimal count of goods
     */
    public int getMinimalCountOfGoods() {
        return minimalCountOfGoods;
    }

    /**
     * Sets minimal count of goods.
     *
     * @param minimalCountOfGoods the minimal count of goods
     */
    public void setMinimalCountOfGoods(int minimalCountOfGoods) {
        this.minimalCountOfGoods = minimalCountOfGoods;
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Sets discount.
     *
     * @param discount the discount
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
