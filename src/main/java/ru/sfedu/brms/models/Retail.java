package ru.sfedu.brms.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.UUIDConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Retail implements Serializable {
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID id;
    @CsvBindByName
    protected String name;
    private List<Customer> customers;
    private List<Check> checks;

    public Retail() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Retail)) return false;
        Retail retail = (Retail) o;
        return Objects.equals(getId(), retail.getId()) && Objects.equals(getName(), retail.getName()) && Objects.equals(getCustomers(), retail.getCustomers()) && Objects.equals(getChecks(), retail.getChecks());
    }

    @Override
    public String toString() {
        return "Retail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customers=" + customers +
                ", checks=" + checks +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCustomers(), getChecks());
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
