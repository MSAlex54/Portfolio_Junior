package com.reha.utils.validators;

import com.reha.model.dto.EventDto;
import com.reha.model.enums.EventStatuses;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EventDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EventDto event = (EventDto) target;
        if (event.getStatus().equals(EventStatuses.CANCELLED.getTitle()) && ObjectUtils.isEmpty(event.getNote())) {
            errors.rejectValue("note", "note.is.empty", "You must specify the reason for canceling the procedure");
        }
    }
}
