package com.excilys.database.persistence;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * . Database connection handler (singleton)
 *
 * @author Yann Mougenel
 *
 */
public enum DatabaseConnection {
    INSTANCE;

    private static String USERBD = null;
    private static String PASSWORDBD = null;
    private static String URL = null;
    private static final String PROPERTIES_FILE = "database.properties";

    /*
     * Static code initializing the database parameters
     * Note : driver initialization not required for new jdbc versions
     */
    static {
        try {
            Properties databaseProperties = new Properties();
            InputStream inputStream = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream(PROPERTIES_FILE);
            if (inputStream != null) {
                databaseProperties.load(inputStream);
            } else {
                throw new FileNotFoundException(
                        "property file '" + PROPERTIES_FILE + "' not found in the classpath");
            }
            Class.forName(databaseProperties.getProperty("DRIVER"));
            USERBD = databaseProperties.getProperty("USERBD");
            PASSWORDBD = databaseProperties.getProperty("PASSWORDBD");
            URL = databaseProperties.getProperty("URL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        return DriverManager.getConnection(URL, USERBD, PASSWORDBD);
    }

}
