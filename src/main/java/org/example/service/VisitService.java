package org.example.service;

import org.example.dao.IGenericDao;
import org.example.models.Visit;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Service
public class VisitService implements IModelService<Visit> {

    private IGenericDao<Visit> genericDao;

    public VisitService(IGenericDao<Visit> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public void save(Visit visit) {
        genericDao.save(Optional.ofNullable(visit)
                .filter(x -> Objects.isNull(x.getId()))
                .orElseThrow());

    }
}
