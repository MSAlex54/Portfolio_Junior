package com.reha.controllers;

import com.reha.model.dto.UserRegistrationDto;
import com.reha.model.entity.Role;
import com.reha.services.UserService;
import com.reha.utils.validators.PasswordUpdateValidator;
import com.reha.utils.validators.UserCreationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class UserController {

    private static final String VIEWS_USER = "users/UserForm";
    private static final String VIEWS_PASS = "users/UpdatePasswordForm";
    private static final String VIEWS_USERS_LIST = "users/UserList";
    private final UserService userService;
    private final UserCreationValidator validator;
    private final PasswordUpdateValidator passValidator;

    @Autowired
    public UserController(UserService userService, UserCreationValidator validator, PasswordUpdateValidator passValidator) {
        this.userService = userService;
        this.validator = validator;
        this.passValidator = passValidator;
    }

    @ModelAttribute("roles")
    public Collection<Role> getRolesList() {
        return userService.getAllRoles();
    }

    @GetMapping("/user")
    public String getUserForm(Model model, @ModelAttribute("user") UserRegistrationDto user) {
        return VIEWS_USER;
    }

    @PostMapping("/user")
    public String addNewUser(@ModelAttribute("user") @Valid UserRegistrationDto user, BindingResult result) {
        validator.validate(user, result);
        if (result.hasErrors()) {
            return VIEWS_USER;
        }
        userService.createUser(user);
        return "redirect:/user/list/";
    }

    @GetMapping("/user/{userId}")
    public String initGetUser(@PathVariable("userId") long id, Model model) {
        model.addAttribute("user", userService.getUserRegistrationById(id));
        model.addAttribute("roles", getRolesList());
        return VIEWS_USER;
    }

    @GetMapping("/user/{userId}/delete")
    public String initDeleteUser(@PathVariable("userId") long id) {
        userService.deleteUser(id);
        return "redirect:/user/list/";
    }

    @PostMapping("/user/{userId}")
    public String updateUser(@PathVariable("userId") long id,
                             @ModelAttribute("user") @Valid UserRegistrationDto user,
                             BindingResult result) {
        validator.validate(user, result);
        user.setId(id);
        if (result.hasErrors()) {
            return VIEWS_USER;
        }
        userService.updateUser(user);
        return "redirect:/user/list/";
    }

    @GetMapping("/user/list")
    public String getUsersList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return VIEWS_USERS_LIST;
    }

    @GetMapping("/password")
    public String getChangePasswordForm(Model model) {
        model.addAttribute("user", userService.getCurrentUserDto());
        return VIEWS_PASS;
    }

    @PostMapping("/password")
    public String updatePassword(@ModelAttribute("user") @Valid UserRegistrationDto user,
                                 BindingResult result) {
        passValidator.validate(user, result);
        if (result.hasErrors()) {
            return VIEWS_PASS;
        }
        userService.updatePassword(user.getPassword());
        return "redirect:/";
    }

}
