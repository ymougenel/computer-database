package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Database connection handler (singleton)
 * @author Yann Mougenel
 *
 */
public class DatabaseConnection {
	
	private static String userBD = "admincdb";
	private static String passwordBD = "qwerty1234";
	private static String url = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
    private static DatabaseConnection bdRequests;
	
	private DatabaseConnection() {}
	
	public static DatabaseConnection getInstance(){
		if (bdRequests == null)
			bdRequests = new DatabaseConnection();
		return bdRequests;
	}
	
	/* Static code initializing the database driver
		Note: Not required for new jdbc versions
	*/
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	public Connection getConnection()throws SQLException {
		return DriverManager.getConnection(url, userBD, passwordBD);
	}
	

	public static void printfResult(ResultSet results) throws SQLException {
//		ResultSetMetaData rsmd = results.getMetaData();
//		int columnsNumber = rsmd.getColumnCount();
		while (results.next()) {
			String id = results.getString("id");
		    String name =results.getString("name");
		    System.out.println("Id :"+id+"\t name :"+name);
		       //if (i > 1) System.out.print(",  ");
		       //String columnValue = results.getString(i);
		       //System.out.println("");
		}
	}
}
