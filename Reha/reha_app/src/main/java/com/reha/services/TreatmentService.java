package com.reha.services;

import com.reha.dao.interfaces.TreatmentRepository;
import com.reha.mapper.TreatmentMapper;
import com.reha.model.dto.TreatmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository repository;
    private final TreatmentMapper mapper;

    @Autowired
    public TreatmentService(TreatmentRepository repository, TreatmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public TreatmentDto createTreatment(TreatmentDto dto) {
        return mapper.toDto(repository.createTreatment(mapper.toEntity(dto)));
    }

    @Transactional
    public List<TreatmentDto> getAllTreatments() {
        List<TreatmentDto> result = new ArrayList<>();
        repository.findAll().stream().forEach(p -> result.add(mapper.toDto(p)));
        return result;
    }


    @Transactional
    public TreatmentDto updateTreatment(TreatmentDto dto) {
        return mapper.toDto(repository.updateTreatment(mapper.toEntity(dto)));
    }

    @Transactional
    public TreatmentDto getTreatmentById(long id) {
        return mapper.toDto(repository.findById(id));
    }
}
