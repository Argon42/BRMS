package ru.sfedu.brms.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.UUIDConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Retail.
 */
public class Retail implements Serializable {
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
     * The Count of stores.
     */
    @CsvBindByName
    protected int countOfStores;
    private List<Customer> customers;
    private List<StoreCheck> checks;

    /**
     * Instantiates a new Retail.
     */
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

    /**
     * Gets count of stores.
     *
     * @return the count of stores
     */
    public int getCountOfStores() {
        return countOfStores;
    }

    /**
     * Sets count of stores.
     *
     * @param countOfStores the count of stores
     */
    public void setCountOfStores(int countOfStores) {
        this.countOfStores = countOfStores;
    }

    /**
     * Gets checks.
     *
     * @return the checks
     */
    public List<StoreCheck> getChecks() {
        return checks;
    }

    /**
     * Sets checks.
     *
     * @param checks the checks
     */
    public void setChecks(List<StoreCheck> checks) {
        this.checks = checks;
    }

    /**
     * Gets customers.
     *
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets customers.
     *
     * @param customers the customers
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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
}
