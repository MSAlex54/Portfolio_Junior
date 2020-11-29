package com.reha.dao.impl;

import com.reha.dao.interfaces.PatientRepository;
import com.reha.model.entity.Patient;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

    @PersistenceContext
    EntityManager em;

    public Patient findById(long id) {
        return em.find(Patient.class, id);
    }


    @Transactional
    public Patient createPatient(Patient patient) {
        em.persist(patient);
        return patient;
    }

    @Transactional
    public Patient updatePatient(Patient patient) {
        em.merge(patient);
        return patient;
    }

    @Transactional
    public void deletePatient(long id) {
        em.remove(findById(id));
    }

    @Transactional
    public List<Patient> findAll() {
        Query query = em.createQuery("SELECT p FROM patients p ORDER BY p.name");
        return query.getResultList();
    }

    @Transactional
    public Set<String> findAllInsNum() {
        Query query = em.createQuery("SELECT p.insureNum FROM patients p ORDER BY p.name");
        return (Set<String>) query.getResultStream().collect(Collectors.toSet());
    }
}
