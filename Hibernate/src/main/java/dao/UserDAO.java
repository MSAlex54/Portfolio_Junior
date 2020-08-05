package dao;

import models.Auto;
import models.User;

import java.util.List;

public interface UserDAO {
    public User findById(int id);

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    public Auto findAutoById(int id);

    public List<User> findAll();
}
