package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "contact_type")
public class ContactType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", unique = true)
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contactType")
    private Set<Contact> contacts;

    public ContactType() {
    }

    public ContactType(String type, Set<Contact> contacts) {
        this.type = type;
        this.contacts = contacts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
