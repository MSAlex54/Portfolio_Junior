package com.reha.utils.validators;

import com.reha.model.dto.UserRegistrationDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCreationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDto dto = (UserRegistrationDto) target;
        if (dto.getPassword() != null) {
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "confirmPassword.not.matched", " Your password and confirmation password do not match.");
            }
        }
        if (dto.getRoleId().size() == 0) {
            errors.rejectValue("roleId", "roleId.is.empty", "You have select at least one role");
        }

    }
}
