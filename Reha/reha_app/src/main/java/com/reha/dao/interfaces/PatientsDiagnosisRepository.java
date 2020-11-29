package com.reha.dao.interfaces;


import com.reha.model.entity.PatientsDiagnosis;

public interface PatientsDiagnosisRepository {
    PatientsDiagnosis findById(long id);

    PatientsDiagnosis createPatientsDiagnosis(PatientsDiagnosis patientsDiagnosis);

    PatientsDiagnosis updatePatientsDiagnosis(PatientsDiagnosis patientsDiagnosis);

    void deletePatientsDiagnosis(PatientsDiagnosis patientsDiagnosis);
}
