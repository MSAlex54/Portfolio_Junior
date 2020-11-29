package com.reha.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientsDiagnosisDto extends AbstractDto {

    private long patientId;

    private long diagnosisId;

    private boolean active;

}
