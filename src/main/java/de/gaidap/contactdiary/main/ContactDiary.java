package de.gaidap.contactdiary.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ContactDiary {

    private static final Logger logger = LogManager.getLogger(ContactDiary.class);

    protected static final String JDBC_PATH = System.getProperty("user.home") + "/datenbank.db";

    public static void main(String[] args) {
        try {
            final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + JDBC_PATH);
            connection.close();
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
}
