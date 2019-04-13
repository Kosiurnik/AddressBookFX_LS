package sda.lukaszs.addressbookfx.manager;

import sda.lukaszs.addressbookfx.HibernateInit;

import javax.persistence.EntityManager;

public class HibernateEntityManager implements AutoCloseable{
    private EntityManager entityManager;

    public HibernateEntityManager() {
        entityManager = HibernateInit.getEntityManagerFactory().createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void close() throws Exception {
        if (!entityManager.isOpen())
            entityManager.close();
        if(entityManager != null)
            entityManager.clear();
    }
}
