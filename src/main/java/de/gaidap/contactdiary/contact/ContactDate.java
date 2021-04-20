package de.gaidap.contactdiary.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ContactDate {

    private int contactDateId;
    private String date;

    public ContactDate(int contactDateId, String date) {
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

    public static ContactDate createContactDate(ResultSet result) throws SQLException {
        return new ContactDate(result.getInt("cdID"),
                result.getString("cDate"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDate that = (ContactDate) o;
        return contactDateId == that.contactDateId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactDateId);
    }

    @Override
    public String toString() {
        return "ContactDate{" +
                "contactDateId=" + contactDateId +
                ", date='" + date + '\'' +
                '}';
    }
}
