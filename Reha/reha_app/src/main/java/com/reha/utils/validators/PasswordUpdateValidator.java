package com.reha.utils.validators;

import com.reha.model.dto.UserRegistrationDto;
import com.reha.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordUpdateValidator implements Validator {

    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDto dto = (UserRegistrationDto) target;
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirmPassword.not.matched", " Your password and confirmation password do not match.");
        }
        if (!service.passwordMatch(service.getCurrentUserDto().getUsername(), dto.getOldPassword())) {
            errors.rejectValue("oldPassword", "oldPassword.not.matched", "Password is incorrect try again");
        }
    }

}
