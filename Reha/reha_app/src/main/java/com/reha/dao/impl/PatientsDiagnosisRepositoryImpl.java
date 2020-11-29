package com.reha.dao.impl;

import com.reha.dao.interfaces.PatientsDiagnosisRepository;
import com.reha.model.entity.PatientsDiagnosis;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class PatientsDiagnosisRepositoryImpl implements PatientsDiagnosisRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public PatientsDiagnosis findById(long id) {
        return em.find(PatientsDiagnosis.class, id);
    }

    @Override
    @Transactional
    public PatientsDiagnosis createPatientsDiagnosis(PatientsDiagnosis patientsDiagnosis) {
        em.persist(patientsDiagnosis);
        return patientsDiagnosis;
    }

    @Override
    @Transactional
    public PatientsDiagnosis updatePatientsDiagnosis(PatientsDiagnosis patientsDiagnosis) {
        em.merge(patientsDiagnosis);
        return patientsDiagnosis;
    }

    @Override
    @Transactional
    public void deletePatientsDiagnosis(PatientsDiagnosis patientsDiagnosis) {
        em.remove(patientsDiagnosis);
    }
}
