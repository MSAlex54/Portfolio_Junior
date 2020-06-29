package service;

import entities.Contact;
import entities.ContactType;
import entities.Person;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IContact  {

    @WebMethod
    public Contact create();

    @WebMethod
    public Contact findContact(Long id);

    @WebMethod
    public List<Contact> getPersonsContacts(Long personId);

    @WebMethod
    public List<Contact> findAll();

    @WebMethod
    public void updateContact(Long id, Person person, ContactType contactType, String number) ;

    @WebMethod
    public void deleteContact(Long id) ;
}
