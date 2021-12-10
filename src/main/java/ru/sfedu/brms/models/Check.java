package ru.sfedu.brms.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.InstantConverter;
import ru.sfedu.brms.UUIDConverter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Check implements Serializable {
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID id;
    @CsvCustomBindByName(converter = InstantConverter.class)
    protected Instant time;
    @CsvBindByName
    protected float cost;
    @CsvBindByName
    protected int countOfGoods;
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID customerId;

    public Check() {
    }

    public Check(UUID id, Instant time, float cost, int countOfGoods, UUID customerId) {
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
        Check check = (Check) o;
        return Float.compare(check.cost, cost) == 0 && countOfGoods == check.countOfGoods && Objects.equals(id, check.id) && Objects.equals(time, check.time) && Objects.equals(customerId, check.customerId);
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", time=" + time +
                ", cost=" + cost +
                ", countOfGoods=" + countOfGoods +
                ", customerId=" + customerId +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getCountOfGoods() {
        return countOfGoods;
    }

    public void setCountOfGoods(int countOfGoods) {
        this.countOfGoods = countOfGoods;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

}
