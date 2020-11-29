package com.reha.utils.validators;

import com.reha.dao.interfaces.PatientRepository;
import com.reha.model.dto.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class PatientDtoValidator implements Validator {

    private final PatientRepository repository;

    @Autowired
    public PatientDtoValidator(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PatientDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PatientDto patientDto = (PatientDto) target;
        if (ObjectUtils.isEmpty(patientDto.getName())) {
            errors.rejectValue("name", "Name.is.empty", "Can not be empty");
        }

        if (ObjectUtils.isEmpty(patientDto.getBirthDate())) {
            errors.rejectValue("birthDate", "birthDate.is.empty", "You have to set birth date");
        } else if (patientDto.getBirthDate().toLocalDate().isAfter((LocalDate.now()))) {
            errors.rejectValue("birthDate", "birthDate.is.afterToday", "Birth day can not be after today");
        }

        if (ObjectUtils.isEmpty(patientDto.getInsureNum())) {
            errors.rejectValue("insureNum", "Insure.is.empty", "Insure Num can not be empty");
        } else if (repository.findAllInsNum().contains(patientDto.getInsureNum())) {
            errors.rejectValue("insureNum", "Insure.is.already.Exists", "Insure Num alreadey in use");
        }
    }
}

