package org.example.service;

import org.example.dao.IGenericDao;
import org.example.models.Doctor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DoctorService implements IEntityService<Doctor> {

    private IGenericDao<Doctor> genericDao;

    public DoctorService(IGenericDao<Doctor> genericDao) {
        this.genericDao = genericDao;
    }

    @Override
    public void save(Doctor entity) {
        genericDao.save(Optional.ofNullable(entity)
                .filter(x -> Objects.nonNull(x.getId()))
                .orElseThrow(() -> new RuntimeException("Wrong entity")));


    }

    @Override
    public Doctor get(Long id) {
        return genericDao.get(Doctor.class, Optional.ofNullable(id)
                .orElseThrow(() -> new RuntimeException("Id jest nullem")));
    }


}
