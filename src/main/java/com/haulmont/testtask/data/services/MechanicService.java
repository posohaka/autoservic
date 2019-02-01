package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.data.entities.ClientEntity;
import com.haulmont.testtask.data.entities.MechanicEntity;
import com.haulmont.testtask.data.entities.OrdersEntity;
import com.haulmont.testtask.data.services.util.Statistic;
import com.haulmont.testtask.model.Mechanic;
import org.hibernate.Session;

import org.hibernate.query.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class MechanicService {

    public static int addMechanic(Mechanic mechanic)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        MechanicEntity mechanicEntity = new MechanicEntity();
        mechanicEntity.setFirstname(mechanic.getFirstname());
        mechanicEntity.setSecondname(mechanic.getSecondname());
        mechanicEntity.setThirdname(mechanic.getThirdname());
        mechanicEntity.setPayment(mechanic.getPayment());
        session.save(mechanicEntity);
        int id = mechanicEntity.getId();
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static void removeMechanic(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        MechanicEntity mechanicEntity = session.get(MechanicEntity.class, id);
        session.delete(mechanicEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void changeMechanic(Mechanic mechanic)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        MechanicEntity mechanicEntity = session.get(MechanicEntity.class, mechanic.getId());
        mechanicEntity.setFirstname(mechanic.getFirstname());
        mechanicEntity.setSecondname(mechanic.getSecondname());
        mechanicEntity.setThirdname(mechanic.getThirdname());
        mechanicEntity.setPayment(mechanic.getPayment());
        session.update(mechanicEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static List<MechanicEntity> allMechanic()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from MechanicEntity ");
        List<MechanicEntity> mechanicEntities = query.list();
        session.close();
        return mechanicEntities;
    }


    public static MechanicEntity getMechanic(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        MechanicEntity mechanicEntity = session.get(MechanicEntity.class, id);
        session.close();
        return mechanicEntity;
    }


    public static Statistic getStatisticOrdersById(int id1)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Statistic statistic = new Statistic();
        Query query = session.createQuery("select count(*) from OrdersEntity where status = 0 and mechanicEntity.id = :id2");
        query.setParameter("id2", id1);
        statistic.setCountPlan(((Long)query.uniqueResult()).intValue());
        query = session.createQuery("select count(*) from OrdersEntity where status = 1 and mechanicEntity.id = :id2");
        query.setParameter("id2", id1);
        statistic.setCountDone(((Long)query.uniqueResult()).intValue());
        query = session.createQuery("select count(*) from OrdersEntity where status = 2 and mechanicEntity.id = :id2");
        query.setParameter("id2", id1);
        statistic.setCountClientOk(((Long)query.uniqueResult()).intValue());
        session.close();
        return statistic;
    }

}
