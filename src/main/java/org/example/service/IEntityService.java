package org.example.service;

public interface IEntityService<T> {
    void save(T entity);

    T get(Long id);
}
