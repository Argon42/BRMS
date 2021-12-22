package ru.sfedu.brms.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.InstantConverter;
import ru.sfedu.brms.UUIDConverter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Store check.
 */
public class StoreCheck implements Serializable {
    /**
     * The Id.
     */
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID id;
    /**
     * The Time.
     */
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant time;
    /**
     * The Cost.
     */
    @CsvBindByName
    protected float cost;
    /**
     * The Count of goods.
     */
    @CsvBindByName
    protected int countOfGoods;
    /**
     * The Customer id.
     */
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID customerId;

    /**
     * Instantiates a new Store check.
     */
    public StoreCheck() {
    }

    /**
     * Instantiates a new Store check.
     *
     * @param id           the id
     * @param time         the time
     * @param cost         the cost
     * @param countOfGoods the count of goods
     * @param customerId   the customer id
     */
    public StoreCheck(UUID id, Instant time, float cost, int countOfGoods, UUID customerId) {
        this.id = id;
        this.time = time;
        this.cost = cost;
        this.countOfGoods = countOfGoods;
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, cost, countOfGoods, customerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreCheck check = (StoreCheck) o;
        return Float.compare(check.cost, cost) == 0 && countOfGoods == check.countOfGoods && Objects.equals(id, check.id) && Objects.equals(time, check.time) && Objects.equals(customerId, check.customerId);
    }

    @Override
    public String toString() {
        return "StoreCheck{" +
                "id=" + id +
                ", time=" + time +
                ", cost=" + cost +
                ", countOfGoods=" + countOfGoods +
                ", customerId=" + customerId +
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
     * Gets time.
     *
     * @return the time
     */
    public Instant getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(Instant time) {
        this.time = time;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public float getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * Gets count of goods.
     *
     * @return the count of goods
     */
    public int getCountOfGoods() {
        return countOfGoods;
    }

    /**
     * Sets count of goods.
     *
     * @param countOfGoods the count of goods
     */
    public void setCountOfGoods(int countOfGoods) {
        this.countOfGoods = countOfGoods;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public UUID getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

}
