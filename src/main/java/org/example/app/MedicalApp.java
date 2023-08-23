package org.example.app;

import org.example.models.Patient;
import org.example.models.Visit;
import org.example.service.IImportService;
import org.example.service.SimpleMedicalService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ComponentScan(basePackages = "org.example")
public class MedicalApp {
    public static void main(String[] args) {


        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MedicalApp.class);
        IImportService imp = ctx.getBean(IImportService.class);
        imp.importDoctorsDataFromFile();
        imp.importPatientsDataFromFile();
        imp.importVisitsDataFromFile();

        SimpleMedicalService bean = ctx.getBean(SimpleMedicalService.class);
        Map<Patient, List<Visit>> patientWithVisit = bean.getPatientWithVisit(272L);
        LocalDate nextAvailableVisit = bean.findNextAvailableVisit(25L, LocalDate.parse("2006-04-07"));
        System.out.println(nextAvailableVisit);
        System.out.println(patientWithVisit);

    }
}