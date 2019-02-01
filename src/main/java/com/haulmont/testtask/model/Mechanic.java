package com.haulmont.testtask.model;

import com.haulmont.testtask.data.entities.MechanicEntity;

public class Mechanic {

    private int id;
    private String firstname;
    private String secondname;
    private String thirdname;
    private Integer payment;

    public Mechanic(int id, String firstname, String secondname, String thirdname, Integer payment) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
        this.thirdname = thirdname;
        this.payment = payment;
    }

    public Mechanic(String firstname, String secondname, String thirdname, Integer payment) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.thirdname = thirdname;
        this.payment = payment;
    }

    public Mechanic() {
    }

    public Mechanic(MechanicEntity mechanicEntity)
    {
        this.id = mechanicEntity.getId();
        this.firstname = mechanicEntity.getFirstname();
        this.secondname = mechanicEntity.getSecondname();
        this.thirdname = mechanicEntity.getThirdname();
        this.payment = mechanicEntity.getPayment();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return firstname + " " + secondname;
    }
}
