package de.gaidap.contactdiary.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ConnectionService {
    Connection createConnection();
    void bootstrapDatabase (Connection connection, List<String> sqlStatements) throws SQLException;
}
