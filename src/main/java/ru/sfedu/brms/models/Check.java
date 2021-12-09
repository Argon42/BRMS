package ru.sfedu.brms.models;

import java.util.UUID;

public class Check {
    private UUID id;
    private long Time;
    private float cost;
    private int countOfGoods;
    private UUID customerId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
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
