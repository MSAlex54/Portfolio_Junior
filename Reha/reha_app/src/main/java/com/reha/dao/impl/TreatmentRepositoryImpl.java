package com.reha.dao.impl;

import com.reha.dao.interfaces.TreatmentRepository;
import com.reha.model.entity.Treatment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class TreatmentRepositoryImpl implements TreatmentRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Treatment findById(long id) {
        return em.find(Treatment.class, id);
    }

    @Override
    public Treatment createTreatment(Treatment treatment) {
        em.persist(treatment);
        return treatment;
    }

    @Override
    public Treatment updateTreatment(Treatment treatment) {
        em.merge(treatment);
        return treatment;
    }

    @Override
    public void deleteTreatment(long id) {

    }

    @Override
    public List<Treatment> findAll() {
        Query query = em.createQuery("SELECT e FROM treatments e ORDER BY e.name");
        return query.getResultList();
    }
}
