package dao;

import models.Auto;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDAOimpl implements UserDAO{

    public User findById(int id) {
        return getSession().get(User.class, id);
    }

    public void save(User user) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void update(User user) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public Auto findAutoById(int id) {
        return getSession().get(Auto.class, id);
    }

    public List<User> findAll() {
        List <User> users = getSession().createQuery("From User").list();
        return users;
    }

    private Session getSession(){
        return HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
}
