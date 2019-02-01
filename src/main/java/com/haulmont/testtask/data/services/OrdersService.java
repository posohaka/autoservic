package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.data.entities.ClientEntity;
import com.haulmont.testtask.data.entities.MechanicEntity;
import com.haulmont.testtask.data.entities.OrdersEntity;
import com.haulmont.testtask.model.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class OrdersService {

    //private static Session session;

    public static int addOrder(Orders order)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ClientEntity clientEntity = session.get(ClientEntity.class, order.getClient().getId());
        MechanicEntity mechanicEntity = session.get(MechanicEntity.class, order.getMechanic().getId());
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setMechanicEntity(mechanicEntity);
        ordersEntity.setClientEntity(clientEntity);
        ordersEntity.setDescription(order.getDescription());
        ordersEntity.setPrice(order.getPrice());
        session.save(ordersEntity);
        int id = ordersEntity.getId();
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static void removeOrder(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        OrdersEntity ordersEntity = session.get(OrdersEntity.class, id);
        session.delete(ordersEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void changeOrder(Orders order)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        OrdersEntity ordersEntity = session.get(OrdersEntity.class, order.getId());
        ClientEntity clientEntity = session.get(ClientEntity.class, order.getClient().getId());
        MechanicEntity mechanicEntity = session.get(MechanicEntity.class, order.getMechanic().getId());
        ordersEntity.setMechanicEntity(mechanicEntity);
        ordersEntity.setClientEntity(clientEntity);
        ordersEntity.setDescription(order.getDescription());
        ordersEntity.setPrice(order.getPrice());
        ordersEntity.setEndtime(order.getEndtime());
        ordersEntity.setBegintime(order.getBegintime());
        ordersEntity.setStatus(order.getStatus());
        session.update(ordersEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static List<OrdersEntity> allOrders()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity ");
        List<OrdersEntity> ordersEntities = query.list();
        session.close();
        return ordersEntities;
    }

    public static OrdersEntity getOrderById(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        OrdersEntity ordersEntity = session.get(OrdersEntity.class, id);
        session.close();
        return ordersEntity;
    }

    public static List<OrdersEntity> getByClient(String secondname)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity where clientEntity.secondname  like :sn");
        query.setParameter("sn", "%" + secondname + "%");
        List<OrdersEntity> ordersEntities = query.list();
        session.close();
        return ordersEntities;
    }

    public static List<OrdersEntity> getByDescription(String description)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity where description like :dc");
        query.setParameter("dc", "%" + description + "%");
        List<OrdersEntity> ordersEntities = query.list();
        session.close();
        return ordersEntities;
    }

    public static List<OrdersEntity> getByStatusPlan()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity where status = 0");
        List<OrdersEntity> ordersEntities = query.list();
        session.close();
        return ordersEntities;
    }

    public static List<OrdersEntity> getByStatusDone()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity where status = 1");
        List<OrdersEntity> ordersEntities = query.list();
        session.close();
        return ordersEntities;
    }

    public static List<OrdersEntity> getByStatusOk()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from OrdersEntity where status = 2");
        List<OrdersEntity> ordersEntities = query.list();
        session.close();
        return ordersEntities;
    }
}
