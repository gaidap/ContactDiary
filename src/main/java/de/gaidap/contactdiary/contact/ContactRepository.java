package de.gaidap.contactdiary.contact;

import de.gaidap.contactdiary.persistence.ConnectionService;
import de.gaidap.contactdiary.person.PersonDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private static final Logger logger = LogManager.getLogger(ContactRepository.class);

    private final ConnectionService connectionService;

    public ContactRepository(final ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public List<ContactDTO> listAllContacts() {
        final Connection connection = connectionService.createConnection();
        final List<ContactDTO> contacts = new ArrayList<>();
        try (connection) {
            final Statement statement = connection.createStatement();
            final ResultSet result = statement.executeQuery(createListAllContactsSqlStatement());
            while (result.next()) {
                final ContactDTO contact = createContactDTO(result);
                contacts.add(contact);
            }
        } catch (SQLException sqlException) {
            logger.error(sqlException.getMessage(), sqlException);
        }
        return contacts;

    }

    private String createListAllContactsSqlStatement() {
        return "SELECT " +
                "Contact.ID, " +
                "ContactDate.ID as cdID, ContactDate.cDate, " +
                "Person.ID as pID, Person.pName" +
                "FROM Kontakt " +
                "INNER JOIN ContactDate ON ContactDate.ID = ContactDate.contactDateID " +
                "INNER JOIN Person ON Person.ID = Contact.personID";
    }

    private ContactDTO createContactDTO(ResultSet result) throws SQLException {
        final ContactDTO contact = new ContactDTO();
        contact.setContactId(result.getInt("ID"));
        contact.setContactDate(createContactDateDTO(result));
        contact.setPerson(createPersonDTO(result));
        return contact;
    }

    private PersonDTO createPersonDTO(ResultSet result) throws SQLException {
        return new PersonDTO(result.getInt("cdID"),
                result.getString("pName"));
    }

    private ContactDateDTO createContactDateDTO(ResultSet result) throws SQLException {
        return new ContactDateDTO(result.getInt("cdID"),
                result.getString("cdID"));
    }
}
