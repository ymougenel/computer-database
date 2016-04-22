package com.excilys.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class BDExtraction {
	private static String userBD = "admincdb";
	private static String passwordBD = "qwerty1234";
	private static String url = "jdbc:mysql://localhost/computer-database-db";

	public static Connection getConnection() throws SQLException {

		Connection con = DriverManager.getConnection(url, userBD, passwordBD);
		return con;
	}

	public static ResultSet getComputers(Connection con) {
		String query = "SELECT id,name from computer LIMIT 10;";
		ResultSet results = null;

		try {
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
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

	public static void printfResult(ResultSet results, int nbcolums) throws SQLException {
		ResultSetMetaData rsmd = results.getMetaData();
		// int columnsNumber = rsmd.getColumnCount();
		while (results.next()) {
			for (int i = 1; i <= nbcolums; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnValue = results.getString(i);
				System.out.print(columnValue + " " + rsmd.getColumnName(i));
			}
			System.out.println("");
		}
	}

	public static ResultSet selectElementsByName(Connection con, String name) {
		String query = "SELECT id,name from computer WHERE id=?;";
		ResultSet results = null;

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	public static ResultSet selectElementsById(Connection con, int id) {
		String query = "SELECT id,name from computer WHERE id=?;";
		ResultSet results = null;

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			results = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

	private static ResultSet getElements(Connection con, String table) {
		String query = "SELECT id,name from " + table + " LIMIT 10;";
		ResultSet results = null;

		try {
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}

	public static ResultSet getAllComputers(Connection con) {
		return getElements(con, "computer");
	}

	public static ResultSet getAllCompagnies(Connection con) {
		return getElements(con, "company");
	}

	public static void main(String[] args) {
		System.out.println("Beginning connection");
		try {
			Connection con = getConnection();
			// ResultSet rs = BDRequests.getAllCompagnies(con);
			// printfResult(rs);
			ResultSet rs = getAllComputers(con);
			printfResult(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connection established");
	}
}
