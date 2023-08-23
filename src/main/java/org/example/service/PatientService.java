package org.example.service;

import org.example.dao.IGenericDao;
import org.example.models.Patient;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService implements IEntityService<Patient> {

    private IGenericDao<Patient> genericDao;

    public PatientService(IGenericDao<Patient> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public void save(Patient entity) {
        genericDao.save(Optional.ofNullable(entity)
                .filter(x -> Objects.nonNull(x.getId()))
                .orElseThrow());


    }
}
