package com.haulmont.testtask.data.services;

import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.data.entities.ClientEntity;
import com.haulmont.testtask.model.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public static int addClient(Client client)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstname(client.getFirstname());
        clientEntity.setSecondname(client.getSecondname());
        clientEntity.setThirdname(client.getThirdname());
        clientEntity.setPhonenumber(client.getPhonenumber());
        session.save(clientEntity);
        int id = clientEntity.getId();
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static void removeClient(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ClientEntity clientEntity = session.get(ClientEntity.class, id);
        session.delete(clientEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void changeClient(Client client)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ClientEntity clientEntity = session.get(ClientEntity.class, client.getId());
        clientEntity.setFirstname(client.getFirstname());
        clientEntity.setSecondname(client.getSecondname());
        clientEntity.setThirdname(client.getThirdname());
        clientEntity.setPhonenumber(client.getPhonenumber());
        session.update(clientEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static List<ClientEntity> allclient()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ClientEntity ");
        List<ClientEntity> clientEntities = query.list();
        session.close();
        return clientEntities;
    }

    public static ClientEntity getClient(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ClientEntity clientEntity = session.get(ClientEntity.class, id);
        session.close();
        return clientEntity;
    }
}
