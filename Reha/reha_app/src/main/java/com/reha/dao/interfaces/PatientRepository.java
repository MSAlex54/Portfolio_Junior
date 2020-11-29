package com.reha.dao.interfaces;

import com.reha.model.entity.Patient;

import java.util.List;
import java.util.Set;

public interface PatientRepository {
    Patient findById(long id);

    Patient createPatient(Patient p);

    Patient updatePatient(Patient p);

    void deletePatient(long id);

    List<Patient> findAll();

    Set<String> findAllInsNum();
}
