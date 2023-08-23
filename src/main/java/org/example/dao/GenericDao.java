package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;

@Repository
public class GenericDao<T> implements IGenericDao<T> {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(t);
        transaction.commit();
        entityManager.close();
    }
}
