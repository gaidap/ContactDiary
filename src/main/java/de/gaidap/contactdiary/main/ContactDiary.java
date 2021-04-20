package de.gaidap.contactdiary.main;

import de.gaidap.contactdiary.contact.Contact;
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
import java.util.Scanner;

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
        insertKontakte(contactRepository);
        List<Contact> contacts = contactRepository.listAllContacts();
        logger.debug("Fetched following Contacts from DB: ");
        for (Contact contact : contacts) {
            logger.debug(contact);
        }
    }

    private static void insertKontakte(ContactRepository contactRepository) {
        final Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Wen haben Sie heute getroffen? Eingabe mit Enter beenden");
            input = scanner.nextLine();
            if (input.equals("")) {
                break;
            }
            contactRepository.addContact(input);
        }
        scanner.close();
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
                "ContactDate(ID INTEGER PRIMARY KEY AUTOINCREMENT, cDate DATE, UNIQUE(cDate));");
        initialStatements.add("CREATE TABLE IF NOT EXISTS " +
                "ContactPerson(ID INTEGER PRIMARY KEY AUTOINCREMENT, pName VARCHAR, UNIQUE(pName));");
        initialStatements.add("CREATE TABLE IF NOT EXISTS " +
                "Contact(contactDateID INTEGER, contactPersonID INTEGER, FOREIGN KEY (contactDateID) REFERENCES ContactDate (contactDateID), "
                + "FOREIGN KEY (contactPersonID) REFERENCES ContactPerson (contactPersonID), UNIQUE(contactDateID, contactPersonID));");
        try {
            connectionService.bootstrapDatabase(connection, initialStatements);
        } catch (SQLException sqlException) {
            logger.error(sqlException.getMessage(), sqlException);
        }
    }
}
