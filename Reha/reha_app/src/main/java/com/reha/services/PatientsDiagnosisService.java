package com.reha.services;

import com.reha.dao.interfaces.PatientsDiagnosisRepository;
import com.reha.mapper.PatientsDiagnosisMapper;
import com.reha.model.dto.DiagnosisDto;
import com.reha.model.dto.PatientDto;
import com.reha.model.dto.PatientsDiagnosisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientsDiagnosisService {

    private final PatientsDiagnosisRepository repository;
    private final PatientsDiagnosisMapper mapper;
    private final DiagnosisService diagnosisService;

    @Autowired
    public PatientsDiagnosisService(PatientsDiagnosisRepository repository, PatientsDiagnosisMapper mapper, DiagnosisService diagnosisService) {
        this.repository = repository;
        this.mapper = mapper;
        this.diagnosisService = diagnosisService;
    }

    public PatientsDiagnosisDto createPatientsDiagnosis(PatientsDiagnosisDto dto, PatientDto patientDto) {
        dto.setPatientId(patientDto.getId());
        return mapper.toDto(repository.createPatientsDiagnosis(mapper.toEntity(dto)));
    }

    public PatientsDiagnosisDto updatePatientsDiagnosis(PatientsDiagnosisDto dto) {
        return mapper.toDto(repository.updatePatientsDiagnosis(mapper.toEntity(dto)));
    }

    public PatientsDiagnosisDto getPatientsDiagnosisById(long id) {
        return mapper.toDto(repository.findById(id));
    }

    public List<DiagnosisDto> getAllDiagnoses() {
        return diagnosisService.getAllDiagnoses();
    }

}
