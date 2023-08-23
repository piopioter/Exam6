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

    public SimpleMedicalService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public LocalDate findNextAvailableVisit(Long id, LocalDate date) {
        LocalDate availableDate = null;
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement prep = conn.prepareStatement("SELECT MIN(visit_date) AS availableDay FROM" +
                        " Visit WHERE doctor_id = ? AND visit_date > ?")
        ) {
            prep.setLong(1, id);
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


    public Map<Patient, List<Visit>> getPatientWithVisit(Long id) {
        Map<Patient, List<Visit>> visits = new HashMap<>();
        List<Visit> visitList = new ArrayList<>();

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement prep = conn.prepareStatement(" SELECT Patient.* , Visit.* FROM Patient " +
                        "LEFT JOIN Visit ON Patient.id = Visit.patient_id WHERE Patient.id = ?");
        ) {
            prep.setLong(1, id);
            try (
                    ResultSet resultSet = prep.executeQuery()
            ) {
                while (resultSet.next()) {
                    Patient patient = new Patient(
                            resultSet.getLong("id"),
                            resultSet.getString("last_name"),
                            resultSet.getString("name"),
                            resultSet.getString("pesel"),
                            resultSet.getDate("birthday").toLocalDate());
                    visitList.add(new Visit(
                                    resultSet.getLong("doctor_id"),
                                    resultSet.getLong("patient_id"),
                                    resultSet.getDate("visit_date").toLocalDate()
                            )
                    );
                    visits.put(patient, visitList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visits;

    }


}
