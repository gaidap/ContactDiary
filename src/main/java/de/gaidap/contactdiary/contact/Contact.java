package de.gaidap.contactdiary.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Contact {

    private ContactDate contactDate;
    private ContactPerson contactPerson;

    public Contact() {
    }

    public ContactDate getContactDate() {
        return contactDate;
    }

    public void setContactDate(ContactDate contactDate) {
        this.contactDate = contactDate;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public static Contact createContact(ResultSet result) throws SQLException {
        final Contact contact = new Contact();
        contact.setContactDate(ContactDate.createContactDate(result));
        contact.setContactPerson(ContactPerson.createContactPerson(result));
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact that = (Contact) o;
        return contactDate.equals(that.contactDate) && contactPerson.equals(that.contactPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactDate.hashCode() + contactPerson.hashCode());
    }

    @Override
    public String toString() {
        return "Contact{" +
                ", contactDate=" + contactDate +
                ", contactPerson=" + contactPerson +
                '}';
    }
}
