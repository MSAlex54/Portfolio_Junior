package repo;

import entities.Person;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;

@Transactional
@Named
public class PersonRepository {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    public Person create(){
        Person person = new Person(); //create new person
        em.persist(person); //add to the database
        return person;
    }

    //find person by Id
    public Person findPerson(Long id) {
        Person person = em.find(Person.class, id);
        return person;
    }

    //get all persons with this last Name
    public List<Person> findPersonLastName(String lastName){
        return em.createQuery("SELECT p FROM person p WHERE p.last_name = ?1").setParameter(1,lastName).getResultList();
    }

    //get all persons in database
    public List<Person> findAll(){
        return em.createQuery("SELECT p FROM person p", Person.class).getResultList();
    }

    //Update person by Id
    public void updatePerson(Long id, String firstName, String lastName, String middleName, String postion) {
        Person person = em.find(Person.class, id);
        person.setFirst_name(firstName);
        person.setLast_name(lastName);
        person.setMiddle_name(middleName);
        person.setPosition(postion);
        em.merge(person);
    }

    //Remove person from database
    public void deletePerson(Long id) {
        Person person = em.find(Person.class, id);
        em.remove(person);
    }
}
