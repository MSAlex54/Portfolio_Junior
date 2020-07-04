package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    private String first_name;

    @Column(length = 80)
    private String last_name;

    @Column(length = 80)
    private String middle_name;

    @Column
    private String position;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person",cascade = CascadeType.ALL)
    private Set<Contact> contacts;

    public Person() {
    }

    public Person(Long id, String first_name, String last_name, String middle_name, String position, Set<Contact> contacts) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.position = position;
        this.contacts = contacts;
    }

    public Person(String first_name, String last_name, String middle_name, String position, Set<Contact> contacts) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.position = position;
        this.contacts = contacts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
