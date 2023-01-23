package de.dereingerostete.homes.sql;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author DerEingerostete
 * @since 1.0
 */
@RequiredArgsConstructor
public class SQLDatabase {
    private final @NotNull String fileName;
    private Connection connection;

    /**
     * Tries to create a database connection
     * @throws SQLException will be thrown if operation failed
     */
    public void connect() throws SQLException {
        if (isConnected()) return;
        connection = new JDBC().connect("jdbc:sqlite:" + fileName, new Properties());
    }

    /**
     * Disconnects the database connection
     * @throws SQLException will be thrown if operation failed
     */
    public void disconnect() throws SQLException {
        if (isConnected()) connection.close();
    }

    /**
     * Checks if the database connection is open
     * @return true if connected
     */
    public boolean isConnected() {
        try {
            PreparedStatement statement = prepare("show tables;");
            statement.executeQuery().close();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Prepare a sql command
     * @param sql the update to execute
     * @return new PreparedStatement
     * @throws SQLException will be thrown if operation failed
     */
    public PreparedStatement prepare(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /**
     * Creates a new table in the given sql database
     * NOTE: Columns has to be defined in sql code
     *
     * @param table the name of the new table
     * @param columns the list of the columns
     * @throws SQLException will be thrown if operation failed
     */
    public void createTable(String table, String columns) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ");");
        statement.close();
    }

}
