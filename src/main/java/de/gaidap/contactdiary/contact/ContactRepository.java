package de.gaidap.contactdiary.contact;

import de.gaidap.contactdiary.persistence.ConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private static final Logger logger = LogManager.getLogger(ContactRepository.class);

    private final ConnectionService connectionService;

    public ContactRepository(final ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public void addContact(String pName) {
        try (Connection connection = connectionService.createConnection()) {
            int datumID = insertContactDate(connection);
            int personID = insertContactPerson(connection, pName);
            insertContact(connection, datumID, personID);
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    private void insertContact(Connection connection, int datumID, int personID) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO Contact (contactDateID, contactPersonID) values(?, ?);");
        prepStmt.setInt(1, datumID);
        prepStmt.setInt(2, personID);
        try {
            prepStmt.execute();
        } catch (SQLiteException exception) {
            handleUniqueException(exception);
        }
    }

    private int insertContactDate(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            stmt.execute("INSERT INTO ContactDate (cDate) values(date());");
        } catch (SQLiteException exception) {
            handleUniqueException(exception);
        }
        return returnInsertedContactDateId(connection);
    }

    private int returnInsertedContactDateId(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery("SELECT ID FROM ContactDate WHERE cDate = date();");
        return result.getInt(1);
    }

    private int insertContactPerson(Connection connection, String pName) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO ContactPerson (pName) values(?);");
        try {
            prepStmt.setString(1, pName);
            prepStmt.execute();
        } catch (SQLiteException exception) {
            handleUniqueException(exception);
        }
        return returnInsertedContactPersonId(connection, pName);
    }

    private int returnInsertedContactPersonId(Connection connection, String pName) throws SQLException {
        PreparedStatement prepStmt = connection.prepareStatement("SELECT ID FROM ContactPerson WHERE pName = ?;");
        prepStmt.setString(1, pName);
        ResultSet result = prepStmt.executeQuery();
        return result.getInt(1);
    }

    private void handleUniqueException(SQLiteException exception) throws SQLiteException {
        if (!exception.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
            throw exception;
        } else {
            logger.debug(exception.getMessage());
        }
    }

    public List<Contact> listAllContacts() {
        Connection connection = connectionService.createConnection();
        List<Contact> contacts = new ArrayList<>();
        try (connection) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(createListAllContactsSqlStatement());
            while (result.next()) {
                Contact contact = Contact.createContact(result);
                contacts.add(contact);
            }
        } catch (SQLException sqlException) {
            logger.error(sqlException.getMessage(), sqlException);
        }
        return contacts;
    }

    private String createListAllContactsSqlStatement() {
        return "SELECT " +
                "ContactDate.ID as cdID, " +
                "ContactDate.cDate, " +
                "ContactPerson.ID as cpID, " +
                "ContactPerson.pName " +
                "FROM Contact " +
                "INNER JOIN ContactDate ON ContactDate.ID = Contact.contactDateID " +
                "INNER JOIN ContactPerson ON ContactPerson.ID = Contact.contactPersonID;";
    }
}
