package org.example.service;


import org.example.models.Patient;
import org.example.models.Visit;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimpleMedicalService {

    private DataSource dataSource;
    private IEntityService<Patient> entityService;

    public SimpleMedicalService(DataSource dataSource, IEntityService<Patient> entityService) {
        this.dataSource = dataSource;
        this.entityService = entityService;
    }

    public LocalDate findNextAvailableVisit(Long doctorId, LocalDate date) {
        if (doctorId == null)
            throw new IllegalArgumentException("Id jest nullem");
        if(date == null)
            throw new IllegalArgumentException("Data jest  nullem ");

        LocalDate availableDate = null;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement prep = conn.prepareStatement("SELECT MIN(visit_date) AS availableDay  FROM" +
                        " Visit WHERE doctor_id = ? AND visit_date > ?  ")
        ) {
            prep.setLong(1, doctorId);
            prep.setDate(2, Date.valueOf(date));

            try (
                    ResultSet resultSet = prep.executeQuery()
            ) {
                while (resultSet.next())
                    availableDate = resultSet.getDate("availableDay").toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableDate;
    }


    public Map<Patient, List<Visit>> getPatientWithVisits(Long patientId) {

        if (patientId == null)
            throw new IllegalArgumentException("Id jest nullem");

        HashMap<Patient, List<Visit>> visitsMap = new HashMap<>();

        Patient patient = entityService.get(patientId);
        List<Visit> visits = patient.getVisits();
        visitsMap.put(patient, visits);


        return visitsMap;
    }


}
