package de.gaidap.contactdiary.main;

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
                "ContactDate(ID INTEGER PRIMARY KEY AUTOINCREMENT, date DATE, UNIQUE(date));");
        initialStatements.add("CREATE TABLE IF NOT EXISTS " +
                "Person(ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, UNIQUE(name));");
        initialStatements.add("CREATE TABLE IF NOT EXISTS " +
                "Contact(dateID INTEGER, personID INTEGER, FOREIGN KEY (dateID) REFERENCES ContactDate (dateID), "
                + "FOREIGN KEY (personID) REFERENCES Person (personID), UNIQUE(dateID, personID));");
        try {
            connectionService.bootstrapDatabase(connection, initialStatements);
        } catch (SQLException sqlException) {
            logger.error(sqlException.getMessage(), sqlException);
        }
    }
}
