package com.reha.dao.impl;

import com.reha.dao.interfaces.UserRepository;
import com.reha.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        Query query = em.createQuery("SELECT e FROM employees e WHERE e.username = :name");
        query.setParameter("name", name);
        return (User) query.getSingleResult();
    }

    @Override
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        em.merge(user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    public List<User> getUserByRole(String role) {
        Query query = em.createQuery("SELECT u FROM employees u INNER JOIN u.roles r WHERE r.name = :role");
        query.setParameter("role", role);
        return query.getResultList();
    }

    @Override
    public List<User> getAllUsers() {
        Query query = em.createQuery("SELECT e FROM employees e ORDER BY e.fullName");
        return query.getResultList();
    }

}
