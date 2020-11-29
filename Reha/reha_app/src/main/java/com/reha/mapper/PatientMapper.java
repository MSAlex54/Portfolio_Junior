package com.reha.mapper;

import com.reha.model.dto.PatientDto;
import com.reha.model.entity.Patient;
import com.reha.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PatientMapper extends AbstractMapper<Patient, PatientDto> {

    private final ModelMapper mapper;
    private UserService userService;

    @Autowired
    public PatientMapper(ModelMapper mapper, UserService userService) {
        super(Patient.class, PatientDto.class);
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    void mapSpecificFields(Patient source, PatientDto destination) {
        destination.setDoctorId(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getDoctor().getId());
    }

    @Override
    void mapSpecificFields(PatientDto source, Patient destination) {
        destination.setDoctor(userService.getUserById(source.getDoctorId()));
    }

}
