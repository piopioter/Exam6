package org.example.service;

import org.example.dao.IGenericDao;
import org.example.models.Visit;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class VisitService implements IEntityService<Visit> {

    private IGenericDao<Visit> genericDao;

    public VisitService(IGenericDao<Visit> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public void save(Visit entity) {
        genericDao.save(Optional.ofNullable(entity)
                .filter(x -> Objects.isNull(x.getId()))
                .orElseThrow(() -> new RuntimeException("Wrong entity")));

    }

    @Override
    public Visit get(Long id) {
        return genericDao.get(Visit.class, Optional.ofNullable(id)
                .orElseThrow(() -> new RuntimeException("Id jest nullem")));
    }
}
