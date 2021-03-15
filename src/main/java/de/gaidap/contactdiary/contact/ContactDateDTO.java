package de.gaidap.contactdiary.contact;

import java.util.Objects;

public class ContactDateDTO {

    private int contactDateId;
    private String date;

    public ContactDateDTO(int contactDateId, String date) {
        this.contactDateId = contactDateId;
        this.date = date;
    }

    public int getContactDateId() {
        return contactDateId;
    }

    public void setContactDateId(int contactDateId) {
        this.contactDateId = contactDateId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDateDTO that = (ContactDateDTO) o;
        return contactDateId == that.contactDateId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactDateId);
    }

    @Override
    public String toString() {
        return "ContactDateDTO{" +
                "contactDateId=" + contactDateId +
                ", date='" + date + '\'' +
                '}';
    }
}
