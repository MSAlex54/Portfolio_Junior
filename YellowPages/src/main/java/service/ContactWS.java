package service;

import entities.Contact;
import entities.ContactType;
import entities.Person;

import javax.ejb.EJB;
import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "service.IContact")
public class ContactWS implements IContact {

    @EJB
    private ContactServiceLocal service;

    @Override
    public Contact create() {
        return service.create();
    }

    @Override
    public Contact findContact(Long id) {
        return service.findContact(id);
    }

    @Override
    public List<Contact> getPersonsContacts(Long personId) {
        return service.getPersonsContacts(personId);
    }

    @Override
    public List<Contact> findAll() {
        return service.findAll();
    }

    @Override
    public void updateContact(Long id, Person person, ContactType contactType, String number) {
        service.updateContact(id, person, contactType, number);
    }

    @Override
    public void deleteContact(Long id) {
        service.deleteContact(id);
    }
}