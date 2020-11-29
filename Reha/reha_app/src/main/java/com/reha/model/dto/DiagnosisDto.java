package com.reha.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisDto extends AbstractDto {

    private String mkb_code;

    private String mkb_name;

    @ToString.Exclude
    private List<PatientsDiagnosisDto> patientsDiagnoses;

}
