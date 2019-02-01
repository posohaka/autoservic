package com.haulmont.testtask.data;

import com.haulmont.testtask.data.entities.MechanicEntity;
import com.haulmont.testtask.data.entities.OrdersEntity;
import com.haulmont.testtask.data.services.MechanicService;
import com.haulmont.testtask.data.services.OrdersService;
import com.haulmont.testtask.model.Orders;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateUtil
{
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            if (sessionFactory == null)
            {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }
            return sessionFactory;
        } catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public static void shutdown()
    {
        getSessionFactory().close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        shutdown();
    }

    public static void main(String[] args)
    {
        try {

            //ClientService.addClient(client);
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            session.beginTransaction();
//            OrdersEntity ordersEntity = new OrdersEntity();
//            ClientEntity clientEntity = session.get(ClientEntity.class,  0);
//            MechanicEntity mechanicEntity = session.get(MechanicEntity.class, 1);
//            ordersEntity.
            //ClientService.addClient(new Client("fff","dwdq", "gwf", "gas"));
//            ArrayList<ClientEntity> clientEntities = (ArrayList<ClientEntity>) ClientService.allclient();
//            for (ClientEntity clientEntity : clientEntities)
//            {
//                System.out.println(clientEntity);
//            }
            System.out.println(MechanicService.getStatisticOrdersById(0));
//            Orders orders = new Orders(1480, "Тест", 5, 2);
//            OrdersService.addOrder(orders);
            List<MechanicEntity> mechanicEntities = MechanicService.allMechanic();
            for (MechanicEntity mechanicEntity : mechanicEntities)
            {
                System.out.println(mechanicEntity);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            HibernateUtil.shutdown();
        }
    }
}