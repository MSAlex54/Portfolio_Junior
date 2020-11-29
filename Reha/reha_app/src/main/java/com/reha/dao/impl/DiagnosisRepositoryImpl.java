package com.reha.dao.impl;

import com.reha.dao.interfaces.DiagnosisRepository;
import com.reha.model.entity.Diagnosis;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DiagnosisRepositoryImpl implements DiagnosisRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Diagnosis findById(long id) {
        return em.find(Diagnosis.class, id);
    }

    @Override
    public List<Diagnosis> getAllDiagnoses() {
        Query query = em.createQuery("SELECT d FROM diagnoses d");
        return query.getResultList();
    }
}
