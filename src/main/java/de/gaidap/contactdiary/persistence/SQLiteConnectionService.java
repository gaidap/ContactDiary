package de.gaidap.contactdiary.persistence;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLiteConnectionService implements ConnectionService {

    private static final Logger logger = LogManager.getLogger(SQLiteConnectionService.class);

    private final String jdbcPath;

    public SQLiteConnectionService(final String jdbcPath) {
        final String basePath = System.getProperty("user.home");
        final String sqliteBaseUrl = "jdbc:sqlite:";
        this.jdbcPath = (StringUtils.isBlank(jdbcPath)
                ? sqliteBaseUrl + basePath + "/contactDiary.db"
                : sqliteBaseUrl + jdbcPath);
    }

    @Override
    public Connection createConnection() {
        try {
            return DriverManager.getConnection(jdbcPath);
        } catch (SQLException sqlException) {
            logger.error(sqlException.getMessage(), sqlException);
            return null;
        }
    }

    @Override
    public void bootstrapDatabase(final Connection connection, final  List<String> sqlStatements) {
        if (connection == null || sqlStatements == null) {
            return;
        }
        try (connection) {
            final Statement statement = connection.createStatement();
            for (String sqlStatement : sqlStatements) {
                statement.execute(sqlStatement);
            }
        } catch (SQLException sqlException) {
            logger.error(sqlException.getMessage(), sqlException);
        }
    }
}
