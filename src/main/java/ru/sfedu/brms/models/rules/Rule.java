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

public abstract class Rule implements Serializable {
    @CsvBindByName
    protected boolean enable;
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID id;
    @CsvBindByName
    protected String name;
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID retailId;
    @CsvBindByName
    protected String description;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public UUID getRetailId() {
        return retailId;
    }

    public void setRetailId(UUID retailId) {
        this.retailId = retailId;
    }

    public Retail getRetail() {
        return retail;
    }

    public void setRetail(Retail retail) {
        this.retail = retail;
    }

    public abstract RuleTypes getRuleType();

    public abstract RuleValidateType getValidateType();
}
