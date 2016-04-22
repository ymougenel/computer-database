package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database connection handler (singleton)
 * 
 * @author Yann Mougenel
 *
 */
public class DatabaseConnection {

	private static String USERBD = "admincdb";
	private static String PASSWORDBD = "qwerty1234";
	private static String URL = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static DatabaseConnection bdRequests;

	/*
	 * Static code initializing the database driver Note: Not required for new
	 * jdbc versions
	 */
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private DatabaseConnection() {
	}

	public static synchronized DatabaseConnection getInstance() {
		if (bdRequests == null)
			bdRequests = new DatabaseConnection();
		return bdRequests;
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
