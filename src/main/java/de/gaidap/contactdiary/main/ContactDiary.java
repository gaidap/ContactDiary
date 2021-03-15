package de.gaidap.contactdiary.main;

import de.gaidap.contactdiary.contact.ContactDTO;
import de.gaidap.contactdiary.contact.ContactRepository;
import de.gaidap.contactdiary.persistence.ConnectionService;
import de.gaidap.contactdiary.persistence.SQLiteConnectionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDiary {

    private static final Logger logger = LogManager.getLogger(ContactDiary.class);

    public static void main(String[] args) {
        final ConnectionService connectionService = new SQLiteConnectionService(createJdbcPath(args));
        final Connection connection = connectionService.createConnection();
        if (connection == null) {
            System.exit(1);
        } else {
            setupDatabaseTables(connectionService, connection);
        }
        ContactRepository contactRepository = new ContactRepository(connectionService);
        List<ContactDTO> contacts = contactRepository.listAllContacts();
        logger.debug("Fetched following Contacts from DB: ");
        for (ContactDTO contact : contacts) {
            logger.debug(contact);
        }
    }

    private static String createJdbcPath(String[] args) {
        final String jdbcPath;
        if (args.length > 0 && StringUtils.isAsciiPrintable(args[0])) {
            jdbcPath = args[0];
        } else {
            jdbcPath = System.getProperty("user.home") + "/contactDiary.db";
        }
        return jdbcPath;
    }

    private static void setupDatabaseTables(final ConnectionService connectionService, final Connection connection) {
        final List<String> initialStatements = new ArrayList<>(3);
        initialStatements.add("CREATE TABLE IF NOT EXISTS " +
                "ContactDateDTO(ID INTEGER PRIMARY KEY AUTOINCREMENT, cDate DATE);");
        initialStatements.add("CREATE TABLE IF NOT EXISTS " +
                "PersonDTO(ID INTEGER PRIMARY KEY AUTOINCREMENT, pName VARCHAR);");
        initialStatements.add("CREATE TABLE IF NOT EXISTS " +
                "ContactDTO(contactDateID INTEGER, personID INTEGER, FOREIGN KEY (contactDateID) REFERENCES ContactDateDTO (contactDateID), "
                + "FOREIGN KEY (personID) REFERENCES PersonDTO (personID), UNIQUE(contactDateID, personID));");
        try {
            connectionService.bootstrapDatabase(connection, initialStatements);
        } catch (SQLException sqlException) {
            logger.error(sqlException.getMessage(), sqlException);
        }
    }
}
