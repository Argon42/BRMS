package ru.sfedu.brms.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.UUIDConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public Customer() {
    }

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

    public UUID getRetailId() {
        return retailId;
    }

    public void setRetailId(UUID retailId) {
        this.retailId = retailId;
    }

    public List<StoreCheck> getChecks() {
        return checks;
    }

    public void setChecks(List<StoreCheck> checks) {
        this.checks = checks;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
