package repo;

import entities.ContactType;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

@Transactional
public class ContactTypeRepository {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    public ContactType create(){
        ContactType cType = new ContactType(); //create new person
        em.persist(cType); //add to the database
        return cType;
    }

    //find type by Id
    public ContactType findContactType(Long id) {
        ContactType cType = em.find(ContactType.class, id);
        return cType;
    }

    //Update type by Id
    public void updateContactType(Long id, String type) {
        ContactType cType = em.find(ContactType.class, id);
        cType.setType(type);
        em.merge(cType);
    }

    //Remove contact from database
    public void deleteContactType(Long id) {
        ContactType cType = em.find(ContactType.class, id);
        em.remove(cType);
    }
}
