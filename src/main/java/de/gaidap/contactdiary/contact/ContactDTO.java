package de.gaidap.contactdiary.contact;

import de.gaidap.contactdiary.person.PersonDTO;

import java.util.Objects;

public class ContactDTO {

    private int contactId;
    private ContactDateDTO contactDate;
    private PersonDTO person;

    public ContactDTO() { }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public ContactDateDTO getContactDate() {
        return contactDate;
    }

    public void setContactDate(ContactDateDTO contactDate) {
        this.contactDate = contactDate;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDTO that = (ContactDTO) o;
        return contactId == that.contactId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId);
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "contactId=" + contactId +
                ", contactDate=" + contactDate +
                ", person=" + person +
                '}';
    }
}
