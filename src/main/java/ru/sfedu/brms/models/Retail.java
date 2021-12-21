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
    @CsvBindByName
    protected int countOfStores;
    private List<Customer> customers;
    private List<StoreCheck> checks;

    public Retail() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Retail)) return false;
        Retail retail = (Retail) o;
        return getCountOfStores() == retail.getCountOfStores() && Objects.equals(getId(), retail.getId()) && Objects.equals(getName(), retail.getName());
    }

    @Override
    public String toString() {
        return "Retail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countOfStores=" + countOfStores +
                ", customers=" + customers +
                ", checks=" + checks +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCountOfStores(), getCustomers(), getChecks());
    }

    public int getCountOfStores() {
        return countOfStores;
    }

    public void setCountOfStores(int countOfStores) {
        this.countOfStores = countOfStores;
    }

    public List<StoreCheck> getChecks() {
        return checks;
    }

    public void setChecks(List<StoreCheck> checks) {
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
