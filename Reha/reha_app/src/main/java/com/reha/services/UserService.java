package com.reha.services;

import com.reha.dao.interfaces.RoleRepository;
import com.reha.dao.interfaces.UserRepository;
import com.reha.model.dto.UserEmployeeDto;
import com.reha.model.dto.UserRegistrationDto;
import com.reha.model.entity.Role;
import com.reha.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public List<UserEmployeeDto> getDoctorsList() {
        return userRepository.getUserByRole("DOCTOR").stream()
                .map(this::toUserEmployeeDto).collect(Collectors.toList());
    }

    public List<UserEmployeeDto> getNurseList() {
        return userRepository.getUserByRole("NURSE").stream()
                .map(this::toUserEmployeeDto).collect(Collectors.toList());
    }

    public UserEmployeeDto getEmploeeDtoByUserId(long id) {
        return toUserEmployeeDto(userRepository.getUserById(id));
    }

    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    public UserRegistrationDto getUserRegistrationById(long id) {
        return toUserRegistrationDto(userRepository.getUserById(id), new UserRegistrationDto());
    }

    public void createUser(UserRegistrationDto dto) {
        dto.setPassword(dto.getUsername());
        userRepository.createUser(toUserEntity(new User(), dto));
    }

    public void updateUser(UserRegistrationDto dto) {
        User entitiy = toUserEntity(userRepository.getUserById(dto.getId()), dto);
        userRepository.updateUser(entitiy);
    }

    public void updatePassword(String newPass) {
        User entitiy = getCurrentUser();
        entitiy.setPassword(encoder.encode(newPass));
        userRepository.updateUser(entitiy);
    }

    public Collection<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    private UserEmployeeDto toUserEmployeeDto(User entity) {
        UserEmployeeDto dto = new UserEmployeeDto();
        dto.setId(entity.getId());
        dto.setUserName(entity.getUsername());
        dto.setName(entity.getFullName());
        return dto;
    }

    private User toUserEntity(User entity, UserRegistrationDto dto) {
        entity.setUsername(dto.getUsername());
        entity.setFullName(dto.getFullName());
        entity.setPassword(encoder.encode(entity.getUsername()));
        entity.setRoles(dto.getRoleId().stream()
                .map(roleRepository::fidById).collect(Collectors.toSet()));
        entity.setEnabled(true);
        return entity;
    }

    private UserRegistrationDto toUserRegistrationDto(User entity, UserRegistrationDto dto) {
        dto.setUsername(entity.getUsername());
        dto.setFullName(entity.getFullName());
        dto.setRoleId(entity.getRoles().stream()
                .map(Role::getId).collect(Collectors.toSet()));
        return dto;
    }

    public Collection<UserEmployeeDto> getAllUsers() {
        return userRepository.getAllUsers().stream().map(this::toUserEmployeeDto).collect(Collectors.toSet());
    }

    public void deleteUser(long id) {
        userRepository.deleteUser(userRepository.getUserById(id));
    }

    public UserRegistrationDto getCurrentUserDto() {
        UserRegistrationDto dto = toUserRegistrationDto(getCurrentUser(), new UserRegistrationDto());
        return dto;
    }

    public boolean passwordMatch(String userName, String pass) {
        String currentPass = userRepository.getUserByName(userName).getPassword();
        return encoder.matches(pass, currentPass);
    }

    protected User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getUserByName(((UserRehaPrincipal) principal).getUsername());
    }

}
