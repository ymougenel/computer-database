package com.excilys.database.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
     * Static code initializing the database driver Note: Not required for new jdbc versions
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
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }

    /* TODO prototype to specify close connection */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERBD, PASSWORDBD);
    }

    public static void printfResult(ResultSet results) throws SQLException {
        // ResultSetMetaData rsmd = results.getMetaData();
        // int columnsNumber = rsmd.getColumnCount();
        while (results.next()) {
            String id = results.getString("id");
            String name = results.getString("name");
            System.out.println("Id :" + id + "\t name :" + name);
            // if (i > 1) System.out.print(", ");
            // String columnValue = results.getString(i);
            // System.out.println("");
        }
    }
}
