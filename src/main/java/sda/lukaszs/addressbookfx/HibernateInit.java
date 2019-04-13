package sda.lukaszs.addressbookfx;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateInit {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public HibernateInit() {
        HibernateInit.entityManagerFactory = Persistence.createEntityManagerFactory("addressbook");
        HibernateInit.entityManager = HibernateInit.entityManagerFactory.createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}
