package com.reha.services;

import com.reha.dao.interfaces.DiagnosisRepository;
import com.reha.mapper.DiagnosisMapper;
import com.reha.model.dto.DiagnosisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosisService {

    private final DiagnosisRepository repository;
    private final DiagnosisMapper mapper;

    @Autowired
    public DiagnosisService(DiagnosisRepository repository, DiagnosisMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public List<DiagnosisDto> getAllDiagnoses() {
        return repository.getAllDiagnoses().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public DiagnosisDto getDiagnosisById(long id) {
        return mapper.toDto(repository.findById(id));
    }


}
