package com.reha.dao.interfaces;

import com.reha.model.entity.Treatment;

import java.util.List;

public interface TreatmentRepository {
    Treatment findById(long id);

    Treatment createTreatment(Treatment treatment);

    Treatment updateTreatment(Treatment treatment);

    void deleteTreatment(long id);

    List<Treatment> findAll();
}
