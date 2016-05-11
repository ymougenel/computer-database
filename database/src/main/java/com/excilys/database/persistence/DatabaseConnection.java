package com.excilys.database.persistence;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariDataSource;

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
    private static HikariDataSource connectionPool;

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

            // Setting the pool (cf https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration )
            connectionPool = new HikariDataSource();
            connectionPool.setUsername(USERBD);
            connectionPool.setPassword(PASSWORDBD);
            connectionPool.setJdbcUrl(URL);
            // Enable cache prepared statements
            connectionPool.addDataSourceProperty("cachePrepStmts", "true");
            // sets the number of prepared statements that the MySQL driver will cache per connection
            connectionPool.addDataSourceProperty("prepStmtCacheSize", "250");
            // This is the maximum length of a prepared SQL statement that the driver will cache (2048 recommended)
            connectionPool.setMaximumPoolSize(20);
            connectionPool.addDataSourceProperty("prepStmtCacheSqlLimit","2048");
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
        return connectionPool.getConnection();
    }

}
