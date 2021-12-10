package ru.sfedu.brms.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.brms.UUIDConverter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class Check implements Serializable {
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID id;
    @CsvBindByName
    protected Instant time;
    @CsvBindByName
    protected float cost;
    @CsvBindByName
    protected int countOfGoods;
    @CsvCustomBindByName(converter = UUIDConverter.class)
    protected UUID customerId;

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
