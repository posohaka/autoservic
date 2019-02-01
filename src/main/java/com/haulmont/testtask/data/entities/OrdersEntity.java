package com.haulmont.testtask.data.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ORDERS", schema = "PUBLIC", catalog = "PUBLIC")
public class OrdersEntity implements Serializable {
    private int id;
    private Date begintime;
    private Date endtime;
    private int price;
    private int status;
    private String description;

    private ClientEntity clientEntity;
    private MechanicEntity mechanicEntity;

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
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BEGINTIME")
    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    @Basic
    @Column(name = "ENDTIME")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "PRICE")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "STATUS", nullable = false, columnDefinition = "int defualt 0")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @OneToMany(cascade = CascadeType.ALL)
//    public Set<ClientEntity> getClientEntities() {
//        return clientEntities;
//    }
//
//    public void setClientEntities(Set<ClientEntity> clientEntities) {
//        this.clientEntities = clientEntities;
//    }
//
//    @OneToMany(cascade = CascadeType.ALL)
//    public Set<MechanicEntity> getMechanicEntities() {
//        return mechanicEntities;
//    }
//
//    public void setMechanicEntities(Set<MechanicEntity> mechanicEntities) {
//        this.mechanicEntities = mechanicEntities;
//    }

    @ManyToOne
    @JoinColumn(name = "IDCLIENT")
    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    @ManyToOne
    @JoinColumn(name = "IDMECHANIK")
    public MechanicEntity getMechanicEntity() {
        return mechanicEntity;
    }

    public void setMechanicEntity(MechanicEntity mechanicEntity) {
        this.mechanicEntity = mechanicEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return id == that.id &&
                price == that.price &&
                status == that.status &&
                Objects.equals(begintime, that.begintime) &&
                Objects.equals(endtime, that.endtime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, begintime, endtime, price, status, description);
    }

    @Override
    public String toString() {
        return "OrdersEntity{" +
                "id=" + id +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", price=" + price +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", clientEntity=" + clientEntity.getId() +
                ", mechanicEntity=" + mechanicEntity.getId() +
                '}';
    }
}
