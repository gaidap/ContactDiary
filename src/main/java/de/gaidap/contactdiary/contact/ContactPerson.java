package de.gaidap.contactdiary.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ContactPerson {

    private int personId;

    private String fullName;

    public ContactPerson(int personId, String fullName) {
        this.personId = personId;
        this.fullName = fullName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static ContactPerson createContactPerson(ResultSet result) throws SQLException {
        return new ContactPerson(result.getInt("cpID"),
                result.getString("pName"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPerson person = (ContactPerson) o;
        return personId == person.personId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
                "personId=" + personId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
