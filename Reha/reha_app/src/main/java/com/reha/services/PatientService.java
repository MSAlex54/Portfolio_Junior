package com.reha.services;

import com.reha.dao.interfaces.PatientRepository;
import com.reha.mapper.PatientMapper;
import com.reha.model.dto.PatientDto;
import com.reha.model.dto.UserEmployeeDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private static final Logger logger = Logger.getLogger(PatientService.class);
    private final PatientRepository repository;
    private final UserService userService;
    private final PatientMapper mapper;

    @Autowired
    public PatientService(PatientRepository repository, UserService userService, PatientMapper mapper) {
        this.repository = repository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Transactional
    public PatientDto createPatient(PatientDto dto) {
        PatientDto patient = mapper.toDto(repository.createPatient(mapper.toEntity(dto)));
        logger.info("Patient ID:" + patient.getId() + " created by user " + userService.getCurrentUser().getUsername());
        return patient;
    }

    public List<PatientDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public PatientDto updatePatient(PatientDto dto) {
        return mapper.toDto(repository.updatePatient(mapper.toEntity(dto)));
    }

    @Transactional
    public PatientDto getPatientById(long id) {
        return mapper.toDto(repository.findById(id));
    }

    @Transactional
    public void deletePatientById(long id) {
        repository.deletePatient(id);
    }

    public Collection<UserEmployeeDto> getDoctorList() {
        return userService.getDoctorsList();
    }

    public UserEmployeeDto getDoctorById(long doctorId) {
        return userService.getEmploeeDtoByUserId(doctorId);
    }
}
