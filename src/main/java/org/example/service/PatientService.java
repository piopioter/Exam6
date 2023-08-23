package org.example.service;

import org.example.dao.IGenericDao;
import org.example.models.Doctor;
import org.example.models.Patient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService implements IModelService<Patient> {

    private IGenericDao<Patient> genericDao;

    public PatientService(IGenericDao<Patient> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public void save(Patient patient) {
        genericDao.save(Optional.ofNullable(patient)
                .filter(x -> Objects.nonNull(x.getId()))
                .orElseThrow());


    }
}
