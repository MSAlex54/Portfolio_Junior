package com.reha.mapper;

import com.reha.dao.interfaces.PatientRepository;
import com.reha.dao.interfaces.TreatmentRepository;
import com.reha.model.dto.AssignmentDto;
import com.reha.model.entity.Assignment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AssignmentMapper extends AbstractMapper<Assignment, AssignmentDto> {

    private final TreatmentRepository treatmentRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper mapper;

    @Autowired
    public AssignmentMapper(TreatmentRepository treatmentRepository, PatientRepository patientRepository, ModelMapper mapper) {
        super(Assignment.class, AssignmentDto.class);
        this.treatmentRepository = treatmentRepository;
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Override
    public Assignment toEntity(AssignmentDto dto) {
        Assignment entity = super.toEntity(dto);
        return entity;
    }

    @Override
    public AssignmentDto toDto(Assignment entity) {
        AssignmentDto dto = super.toDto(entity);
        dto.setDaysOfWeek(Objects.isNull(entity) || Objects.isNull(entity.getDaysOfWeek()) ? null :
                Arrays.asList(entity.getDaysOfWeek().
                        replace("[", "").
                        replace("]", "").
                        replace(",", "").
                        trim().split(" "))
        );
        dto.setMoments(Objects.isNull(entity) || Objects.isNull(entity.getMoments()) ? null :
                Arrays.asList(entity.getMoments().
                        replace("[", "").replace("]", "").
                        replace(",", "").trim().split(" "))
        );
        return dto;
    }

    @Override
    public void mapSpecificFields(Assignment source, AssignmentDto destination) {
        destination.setPatientId(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getPatient().getId());
        destination.setTreatmentId(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getTreatment().getId());
    }

    @Override
    void mapSpecificFields(AssignmentDto source, Assignment destination) {
        destination.setPatient(patientRepository.findById(source.getPatientId()));
        destination.setTreatment(treatmentRepository.findById(source.getTreatmentId()));
        destination.setDaysOfWeek(source.getDaysOfWeek().toString());
        destination.setMoments(source.getMoments().toString());
    }

}
