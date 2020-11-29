package com.reha.dao.interfaces;

import com.reha.model.entity.User;

import java.util.List;

public interface UserRepository {
    User getUserById(long id);

    User getUserByName(String name);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(User user);

    List<User> getUserByRole(String role);

    List<User> getAllUsers();
}
