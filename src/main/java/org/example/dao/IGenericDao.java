package org.example.dao;

import org.example.models.Doctor;

public interface IGenericDao<T> {
    void save(T t);
}
