package com.reha.utils.validators;

import com.reha.model.dto.AssignmentDto;
import com.reha.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class AssignmentDtoValidator implements Validator {

    private final TreatmentService treatmentService;

    @Autowired
    public AssignmentDtoValidator(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AssignmentDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AssignmentDto assignmentDto = (AssignmentDto) target;
        if (ObjectUtils.isEmpty(assignmentDto.getStartDate())) {
            errors.rejectValue("startDate", "startDate.is.empty", "You have to set start date");
        } else if (assignmentDto.getStartDate().toLocalDate().isBefore(LocalDate.now())) {
            errors.rejectValue("startDate", "startDate.is.beforeToday", "You can set assignment before today");
        }

        if (ObjectUtils.isEmpty(assignmentDto.getFinishDate())) {
            errors.rejectValue("finishDate", "finishDate.is.empty", "You have to set finish date");
        } else if (
                assignmentDto.getFinishDate().toLocalDate().isBefore(LocalDate.now())
                        || assignmentDto.getFinishDate().toLocalDate().isBefore(assignmentDto.getStartDate().toLocalDate())) {
            errors.rejectValue("finishDate", "finishDate.is.beforeToday", "You can set assignment before today or start date");
        }
        if (ObjectUtils.isEmpty(assignmentDto.getMoments())) {
            errors.rejectValue("moments", "empty.moments", "You have to set at least one time moment");
        }
        if (treatmentService.getTreatmentById(assignmentDto.getTreatmentId()).isDrug()) {
            if (ObjectUtils.isEmpty(assignmentDto.getDose())) {
                errors.rejectValue("dose", "empty.dose", "Please set dose for medication");
            }
        }

        switch (assignmentDto.getIntervalType()) {
            case "Day":
                break;
            case "Week":
                if (ObjectUtils.isEmpty(assignmentDto.getDaysOfWeek())) {
                    errors.rejectValue("daysOfWeek", "daysOfWeek.is.empty", "You have select at least one day");
                }
                break;
            case "Month":
                break;
            default:
                break;
        }
    }
}
