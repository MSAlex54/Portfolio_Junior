package com.reha.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "diagnoses")
public class Diagnosis extends AbstractEntity {

    @Column(name = "mkb_code")
    private String mkb_code;

    @Column(name = "mkb_name")
    private String mkb_name;

    @ToString.Exclude
    @OneToMany(mappedBy = "diagnosis", fetch = FetchType.EAGER)
    private List<PatientsDiagnosis> patientsDiagnoses;
}
