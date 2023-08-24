package org.example.dao;


public interface IGenericDao<T> {
    void save(T t);

    T get(Class<T> entity, Long id);
}
