package com.reha.services;

import com.reha.dao.interfaces.AssignmentRepository;
import com.reha.mapper.AssignmentMapper;
import com.reha.model.dto.AssignmentDto;
import com.reha.model.entity.Assignment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * service for all major operations with Assignment
 */
@Service
@Transactional
public class AssignmentService {

    private static final Logger logger = Logger.getLogger(AssignmentService.class);
    private final ClinicService clinicService;
    private final AssignmentRepository repository;
    private final AssignmentMapper mapper;
    private final UserService userService;

    @Autowired
    public AssignmentService(ClinicService clinicService, AssignmentRepository repository, AssignmentMapper mapper, UserService userService) {
        this.clinicService = clinicService;
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    /**
     * create new assignment
     * @param dto assignmentDto from front side
     * @param patientId id of patient
     */
    public void createAssignment(AssignmentDto dto, int patientId) {
        logger.info("Create new assignment for patient ID:" + patientId + " by user: " + userService.getCurrentUser().getUsername());
        dto.setPatientId(patientId);
        Assignment entity = repository.createAssignment(mapper.toEntity(dto));
        clinicService.generateEvents(entity, entity.getStartDate().toLocalDate(), dto.getMoments(), dto.getDaysOfWeek());
    }

    /**
     * @param dto assignmentDto to update
     * @return saved dto
     */
    public AssignmentDto updateAssignment(AssignmentDto dto) {
        logger.info("Create new assignment for patient ID:" + dto.getPatientId() + " by user: " + userService.getCurrentUser().getUsername());
        repository.updateAssignment(mapper.toEntity(dto));
        clinicService.updateEvents(dto);
        return dto;
    }

    public AssignmentDto getAssignmentById(long id) {
        AssignmentDto assignmentDto = mapper.toDto(repository.findById(id));
        return assignmentDto;
    }

    public AssignmentDto getNewAssignmetnDto() {
        return new AssignmentDto();
    }

}
