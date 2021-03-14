package de.gaidap.contactdiary.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ContactDiary {

    public static void main(String[] args) {
        String datenbankURL = System.getProperty("user.home") + "/" + "datenbank.db";
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + datenbankURL);
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
