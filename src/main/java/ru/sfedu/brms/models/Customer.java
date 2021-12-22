package ru.sfedu.brms.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.UUIDConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Customer.
 */
public class Customer implements Serializable {
    @CsvCustomBindByName(converter = UUIDConverter.class)
    private UUID id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String phoneNumber;
    @CsvBindByName
    private String email;
    @CsvCustomBindByName(converter = UUIDConverter.class)
    private UUID retailId;
    private List<StoreCheck> checks;

    /**
     * Instantiates a new Customer.
     */
    public Customer() {
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id          the id
     * @param name        the name
     * @param phoneNumber the phone number
     * @param email       the email
     */
    public Customer(UUID id, String name, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPhoneNumber(), getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId()) && Objects.equals(getName(), customer.getName()) && Objects.equals(getPhoneNumber(), customer.getPhoneNumber()) && Objects.equals(getEmail(), customer.getEmail())&& Objects.equals(getRetailId(), customer.getRetailId());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", retailId='" + retailId + '\'' +
                '}';
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

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
