package ru.sfedu.brms.models.rules;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.UUIDConverter;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.enums.RuleValidateType;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Rule.
 */
public abstract class Rule implements Serializable {
    /**
     * The Enable.
     */
    @CsvBindByName
    protected boolean enable;
    /**
     * The Id.
     */
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID id;
    /**
     * The Name.
     */
    @CsvBindByName
    protected String name;
    /**
     * The Retail id.
     */
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID retailId;
    /**
     * The Description.
     */
    @CsvBindByName
    protected String description;
    /**
     * The Retail.
     */
    protected Retail retail;

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule)) return false;
        Rule rule = (Rule) o;
        return Objects.equals(getId(), rule.getId()) && getName().equals(rule.getName()) && Objects.equals(getDescription(), rule.getDescription());
    }

    @Override
    public String toString() {
        return "Rule{" +
                "enable=" + enable +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", retailId=" + retailId +
                ", description='" + description + '\'' +
                ", retail=" + retail +
                '}';
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Is enable boolean.
     *
     * @return the boolean
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Sets enable.
     *
     * @param enable the enable
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Gets retail id.
     *
     * @return the retail id
     */
    public UUID getRetailId() {
        return retailId;
    }

    /**
     * Sets retail id.
     *
     * @param retailId the retail id
     */
    public void setRetailId(UUID retailId) {
        this.retailId = retailId;
    }

    /**
     * Gets retail.
     *
     * @return the retail
     */
    public Retail getRetail() {
        return retail;
    }

    /**
     * Sets retail.
     *
     * @param retail the retail
     */
    public void setRetail(Retail retail) {
        this.retail = retail;
    }

    /**
     * Gets rule type.
     *
     * @return the rule type
     */
    public abstract RuleTypes getRuleType();

    /**
     * Gets validate type.
     *
     * @return the validate type
     */
    public abstract RuleValidateType getValidateType();
}
