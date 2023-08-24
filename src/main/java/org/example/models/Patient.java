package org.example.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Patient implements Serializable {

    private static final long serialVersionUID = 42L;

    @Id
    private Long id;
    @Column(name = "last_name")
    private String lastName;
    private String name;
    private String pesel;
    private LocalDate birthday;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<Visit> visits;

    public Patient() {
    }


    public Patient(Long id, String lastName, String name, String pesel, LocalDate birthday, List<Visit> visits) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.pesel = pesel;
        this.birthday = birthday;
        this.visits = visits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }


    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(lastName, patient.lastName) && Objects.equals(name, patient.name) && Objects.equals(pesel, patient.pesel) && Objects.equals(birthday, patient.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, name, pesel, birthday);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", pesel='" + pesel + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
