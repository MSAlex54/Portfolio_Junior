package service;

import entities.Contact;
import entities.ContactType;
import entities.Person;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ContactServiceLocal {

    //Create New Contact
    public Contact create();

    //find Contact by Id
    public Contact findContact(Long id);

    //get all contacts of person, by person id
    public List<Contact> getPersonsContacts(Long personId);

    //get all contacts in database
    public List<Contact> findAll();

    //Update contact by Id
    public void updateContact(Long id, Person person, ContactType contactType, String number) ;

    //Remove contact from database
    public void deleteContact(Long id) ;
}
