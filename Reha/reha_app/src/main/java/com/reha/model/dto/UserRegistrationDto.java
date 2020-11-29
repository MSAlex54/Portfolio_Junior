package com.reha.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserRegistrationDto extends AbstractDto {

    @NotEmpty(message = "Username can'''t be blank")
    private String username;

    @NotEmpty(message = "Employee name can'''t be blank")
    private String fullName;

    private String password;

    private String confirmPassword;

    private String oldPassword;

    private Set<Long> roleId;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(@NotEmpty(message = "Username can'''t be blank") String username,
                               @NotEmpty(message = "Employee name can'''t be blank") String fullName,
                               String password, String confirmPassword, String oldPassword, Set<Long> roleId) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.oldPassword = oldPassword;
        this.roleId = roleId;
    }

}
