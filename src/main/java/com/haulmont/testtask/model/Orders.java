package com.haulmont.testtask.model;

import com.haulmont.testtask.data.entities.OrdersEntity;

import java.util.Date;

public class Orders {

    private int id;
    private Date begintime;
    private Date endtime;
    private int price;
    private int status;
    private String description;
    private Client client;
    private Mechanic mechanic;

    public Orders(int id, Date begintime, Date endtime, int price, int status, String description, Client client, Mechanic mechanic) {
        this.id = id;
        this.begintime = begintime;
        this.endtime = endtime;
        this.price = price;
        this.status = status;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
    }

    public Orders() {
    }

    public Orders(Date begintime, Date endtime, int price, int status, String description, Client client, Mechanic mechanic) {
        this.begintime = begintime;
        this.endtime = endtime;
        this.price = price;
        this.status = status;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
    }

    public Orders(int price, String description, Client client, Mechanic mechanic) {
        this.price = price;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
    }

    public Orders(OrdersEntity ordersEntity)
    {
        this.id = ordersEntity.getId();
        this.begintime = ordersEntity.getBegintime();
        this.endtime = ordersEntity.getEndtime();
        this.price = ordersEntity.getPrice();
        this.status = ordersEntity.getStatus();
        this.description = ordersEntity.getDescription();
        this.client = new Client(ordersEntity.getClientEntity());
        this.mechanic = new Mechanic(ordersEntity.getMechanicEntity());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }
}
