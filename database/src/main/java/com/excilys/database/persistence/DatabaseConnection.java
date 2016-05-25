package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

/**
 * . Database connection handler (singleton)
 *
 * @author Yann Mougenel
 *
 */
public enum DatabaseConnection {
    INSTANCE;

    private static HikariDataSource connectionPool;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }

    /**
     * Access to a database connection Note : the connection must be closed by the client
     *
     * @return a new Connection()
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public static void closePipe(AutoCloseable c) {
        try {
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
}
