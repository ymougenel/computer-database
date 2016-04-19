package com.excilys.database.persistence;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;

import com.sun.rowset.JdbcRowSetImpl;



public class BDRequests {
	
	private static String userBD = "admincdb";
	private static String passwordBD = "qwerty1234";
	private static String url = "jdbc:mysql://localhost/computer-database-db";
    //private Connection conn;
    private Statement statement;
    private static BDRequests bdRequests;
	
	private BDRequests() {}
	
	public static BDRequests getInstance(){
		if (bdRequests == null)
			bdRequests = new BDRequests();
		return bdRequests;
	}
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	private void connect() throws SQLException {
		//conn = DriverManager.getConnection(url, userBD, passwordBD);
	}

	public RowSet getRowSet() throws SQLException {
		//Runnable r1 = () -> System.out.println(this);
		JdbcRowSet rs = new JdbcRowSetImpl();
		rs.setUrl(url);
		rs.setPassword(passwordBD);
		rs.setUsername(userBD);
		return rs;
		
	}
	
	/**
    *
    * @param query String The query to be executed
    * @return a ResultSet object containing the results or null if not available
    * @throws SQLException
    */
    public ResultSet query(String query) throws SQLException{
        System.out.println("### +i query called for : "+query);
    	connect();
    	//statement = conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        //disconnect();
        return res;
    }
    
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
    	System.out.println("### +i insert called for : "+insertQuery);
    	connect();
        //statement = conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }

	public static void printfResult(ResultSet results) throws SQLException {
		ResultSetMetaData rsmd = results.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
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
