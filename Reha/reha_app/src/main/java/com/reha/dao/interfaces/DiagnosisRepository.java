package com.reha.dao.interfaces;

import com.reha.model.entity.Diagnosis;

import java.util.List;

public interface DiagnosisRepository {

    Diagnosis findById(long id);

    List<Diagnosis> getAllDiagnoses();
}
