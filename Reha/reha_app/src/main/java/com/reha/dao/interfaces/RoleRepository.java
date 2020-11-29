package com.reha.dao.interfaces;


import com.reha.model.entity.Role;

import java.util.List;

public interface RoleRepository {

    Role findByName(String name);

    Role fidById(long id);

    List<Role> getAllRoles();

}
