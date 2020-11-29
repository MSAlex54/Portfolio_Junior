package com.reha.mapper;

import com.reha.dao.interfaces.DiagnosisRepository;
import com.reha.dao.interfaces.PatientRepository;
import com.reha.model.dto.PatientsDiagnosisDto;
import com.reha.model.entity.PatientsDiagnosis;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class PatientsDiagnosisMapper extends AbstractMapper <PatientsDiagnosis, PatientsDiagnosisDto> {

    private final ModelMapper mapper;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientsDiagnosisMapper(PatientRepository patientRepository, ModelMapper mapper) {
        super(PatientsDiagnosis.class,PatientsDiagnosisDto.class);
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Override
    void mapSpecificFields(PatientsDiagnosis source, PatientsDiagnosisDto destination) {
        destination.setPatientId(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getPatient().getId());
    }

    @Override
    void mapSpecificFields(PatientsDiagnosisDto source, PatientsDiagnosis destination) {
        destination.setPatient(patientRepository.findById(source.getPatientId()));
    }
}
