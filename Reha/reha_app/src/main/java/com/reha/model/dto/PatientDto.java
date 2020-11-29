package com.reha.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reha.model.entity.Assignment;
import com.reha.model.entity.Event;
import com.reha.model.entity.PatientsDiagnosis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto extends AbstractDto {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String insureNum;

    private long doctorId;

    @ToString.Exclude
    private List<Assignment> assignments;

    @ToString.Exclude
    private List<PatientsDiagnosis> patientsDiagnoses;

    @ToString.Exclude
    private List<Event> patientEvents;

    private boolean healthy;

    public int getAge() {
        return Period.between(getBirthDate().toLocalDate(), LocalDate.now()).getYears();
    }

}
