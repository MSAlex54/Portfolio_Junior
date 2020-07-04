package repo;

import entities.Contact;
import entities.ContactType;
import entities.Person;
import service.ContactServiceLocal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;

@Transactional
@Named
public class ContactBean implements ContactServiceLocal {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    @Override
    public Contact create(){
        Contact contact = new Contact(); //create new person
        em.persist(contact); //add to the database
        return contact;
    }

    @Override
    //find Contact by Id
    public Contact findContact(Long id) {
        Contact contact = em.find(Contact.class, id);
        return contact;
    }

    @Override
    //get all contacts of person, by person id
    public List<Contact> getPersonsContacts(Long personId){
        List<Contact> list = em.createQuery("SELECT c FROM contact c WHERE c.person_id Like ?1").setParameter(1,personId).getResultList();
        return list;
    }

    @Override
    //get all contacts in database
    public List<Contact> findAll(){
        Query query = em.createQuery("SELECT c FROM contact c", Contact.class);
        List<Contact> list = query.getResultList();
        return list;
    }

    @Override
    //Update contact by Id
    public void updateContact(Long id, Person person, ContactType contactType, String number) {
        Contact contact = em.find(Contact.class, id);
        contact.setContactType(contactType);
        contact.setPerson(person);
        contact.setNumber(number);
        em.merge(contact);
    }

    @Override
    //Remove contact from database
    public void deleteContact(Long id) {
        Contact contact = em.find(Contact.class, id);
        if (contact!=null) {
            em.remove(contact);
        }
    }
}