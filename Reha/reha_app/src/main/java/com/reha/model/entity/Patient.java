package com.reha.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "insure_num")
    private String insureNum;

    @ToString.Exclude
    @Column
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Assignment> assignments;

    @ToString.Exclude
    @Column
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientsDiagnosis> patientsDiagnoses;

    @ToString.Exclude
    @Column
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Event> patientEvents;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Column(name = "healthy")
    private boolean healthy;

}