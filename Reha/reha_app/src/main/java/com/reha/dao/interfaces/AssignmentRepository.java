package com.reha.dao.interfaces;

import com.reha.model.entity.Assignment;

import java.util.List;

public interface AssignmentRepository {
    Assignment findById(long id);

    List<Assignment> getAllPatientsAssignment(long patientId);

    Assignment createAssignment(Assignment a);

    Assignment updateAssignment(Assignment a);

    void deleteAssignment(Assignment a);
}
