package com.reha.dao.impl;

import com.reha.dao.interfaces.AssignmentRepository;
import com.reha.model.entity.Assignment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class AssignmentRepositoryImpl implements AssignmentRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Assignment findById(long id) {
        return em.find(Assignment.class, id);
    }

    @Override
    public List<Assignment> getAllPatientsAssignment(long patientId) {
        Query query = em.createQuery("SELECT a FROM assignment a WHERE a.patient.id = :pat_id");
        query.setParameter("pat_id", patientId);
        return query.getResultList();
    }

    @Override
    public Assignment createAssignment(Assignment a) {
        em.persist(a);
        return a;
    }

    @Override
    public Assignment updateAssignment(Assignment a) {
        em.merge(a);
        return a;
    }

    @Override
    public void deleteAssignment(Assignment a) {
        em.remove(a);
    }
}
