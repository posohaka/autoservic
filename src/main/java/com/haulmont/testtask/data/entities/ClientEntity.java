package com.haulmont.testtask.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CLIENT", schema = "PUBLIC", catalog = "PUBLIC")
public class ClientEntity implements Serializable {
    private int id;
    private String firstname;
    private String secondname;
    private String thirdname;
    private String phonenumber;

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
    @Column(name = "PHONENUMBER")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    private Set<OrdersEntity> ordersEntities = new HashSet<OrdersEntity>();

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrdersEntity> getOrdersEntities() {
        return ordersEntities;
    }

    public void setOrdersEntities(Set<OrdersEntity> ordersEntities) {
        this.ordersEntities = ordersEntities;
    }

    public void addOrdersEntities(OrdersEntity ordersEntities) {
        ordersEntities.setClientEntity(this);
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
        ClientEntity that = (ClientEntity) o;
        return id == that.id &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(secondname, that.secondname) &&
                Objects.equals(thirdname, that.thirdname) &&
                Objects.equals(phonenumber, that.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, secondname, thirdname, phonenumber);
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", thirdname='" + thirdname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
