package org.example.service;

import org.example.models.Doctor;
import org.example.models.Patient;
import org.example.models.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Service
@PropertySources({
        @PropertySource("classpath:application-prod.properties"),
        @PropertySource("classpath:application-test.properties")
})
public class ImportService implements IImportService {

    @Value("${doctorsFile}")
    private Resource doctorsFile;
    @Value("${patientsFile}")
    private Resource patientsFile;
    @Value("${visitsFile}")
    private Resource visitsFile;
    private DoctorService doctorService;
    private PatientService patientService;
    private VisitService visitService;

    public ImportService(DoctorService doctorService, PatientService patientService, VisitService visitService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.visitService = visitService;
    }


    @Override
    public void importDoctorsDataFromFile() {

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(doctorsFile.getInputStream()))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Id_lekarza"))
                    continue;
                String[] properties = line.split("\t");
                Long id = Long.parseLong(properties[0]);
                String lastName = properties[1];
                String name = properties[2];
                String speciality = properties[3];
                LocalDate birthday = LocalDate.parse(properties[4]);
                String nip = properties[5];
                String pesel = properties[6];

                doctorService.save(new Doctor(id, lastName, name, speciality, birthday, nip, pesel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importPatientsDataFromFile() {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(patientsFile.getInputStream()))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Id_pacjenta"))
                    continue;
                String[] properties = line.split("\t");
                Long id = Long.parseLong(properties[0]);
                String lastName = properties[1];
                String name = properties[2];
                String pesel = properties[3];
                LocalDate date = parseFlexibleDate(properties[4]);

                patientService.save(new Patient(id, lastName, name, pesel, date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void importVisitsDataFromFile() {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(visitsFile.getInputStream()))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Id_lekarza"))
                    continue;
                String[] properties = line.split("\t");
                Long doctorId = Long.parseLong(properties[0]);
                Long patientId = Long.parseLong(properties[1]);
                LocalDate visitDate = parseFlexibleDate(properties[2]);

                visitService.save(new Visit(doctorId, patientId, visitDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LocalDate parseFlexibleDate(String date) {
        DateTimeFormatter dtfb = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-M-d")
                .optionalStart()
                .appendPattern("-d")
                .optionalEnd()
                .optionalStart()
                .appendPattern("-MM-d")
                .optionalEnd()
                .optionalStart()
                .appendPattern("-d")
                .optionalEnd()
                .toFormatter();

        return LocalDate.parse(date, dtfb);
    }


}



