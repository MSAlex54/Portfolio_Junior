package com.reha.dao.impl;

import com.reha.dao.interfaces.RoleRepository;
import com.reha.model.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Role findByName(String name) {
        return em.find(Role.class, name);
    }

    @Override
    public Role fidById(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Role> getAllRoles() {
        Query query = em.createQuery("SELECT r FROM roles r ORDER BY r.name");
        return query.getResultList();
    }

}
