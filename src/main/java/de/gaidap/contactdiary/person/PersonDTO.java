package de.gaidap.contactdiary.person;

import java.util.Objects;

public class PersonDTO {

    private int personId;

    private String fullName;

    public PersonDTO(int personId, String fullName) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return personId == personDTO.personId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "personId=" + personId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
