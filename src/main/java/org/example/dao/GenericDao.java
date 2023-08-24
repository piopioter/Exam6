package org.example.dao;

import jakarta.persistence.*;

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


    @Override
    public T get(Class<T> entityType, Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T entity = entityManager.find(entityType, id);
        entityManager.close();
        return entity;
    }

}
