package com.haulmont.testtask.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "MECHANIC", schema = "PUBLIC", catalog = "PUBLIC")
public class MechanicEntity implements Serializable {
    private int id;
    private String firstname;
    private String secondname;
    private String thirdname;
    private Integer payment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "SECONDNAME")
    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    @Basic
    @Column(name = "THIRDNAME")
    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    @Basic
    @Column(name = "PAYMENT")
    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    private Set<OrdersEntity> ordersEntities = new HashSet<OrdersEntity>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<OrdersEntity> getOrdersEntities() {
        return ordersEntities;
    }

    public void setOrdersEntities(Set<OrdersEntity> ordersEntities) {
        this.ordersEntities = ordersEntities;
    }

    public void addOrdersEntities(OrdersEntity ordersEntities) {
        ordersEntities.setMechanicEntity(this);
        getOrdersEntities().add(ordersEntities);
    }

    public void removeOrdersEntities(OrdersEntity ordersEntity)
    {
        getOrdersEntities().remove(ordersEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MechanicEntity that = (MechanicEntity) o;
        return id == that.id &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(secondname, that.secondname) &&
                Objects.equals(thirdname, that.thirdname) &&
                Objects.equals(payment, that.payment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, secondname, thirdname, payment);
    }

    @Override
    public String toString() {
        return "MechanicEntity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", thirdname='" + thirdname + '\'' +
                ", payment=" + payment +
                '}';
    }
}
