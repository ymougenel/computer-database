package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.database.entities.Company;

public class CompanyDAO extends DAO<Company> {

	@Override
	public Company find(long id) throws SQLException {
		Company cmp;
		String query = "SELECT id, name from company WHERE id = ?;";
		ResultSet results;
		// System.out.println("### +i query called for : "+query +" << "+name);
		Connection con = DatabaseConnection.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setLong(1, id);
		results = stmt.executeQuery();
		cmp = wrapDatabaseResult(results);
		
		stmt.close();
		con.close();
		return cmp;
	}

	@Override
	public Company find(String name) throws SQLException {
		Company cmp;
		String query = "SELECT id, name from company WHERE name = ?;";
		ResultSet results;
		// System.out.println("### +i query called for : "+query +" << "+name);
		Connection con = DatabaseConnection.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, name);
		results = stmt.executeQuery();
		cmp = wrapDatabaseResult(results);
		
		stmt.close();
		con.close();
		return cmp;
	}

	/**
	 * Insert a new computer into the database
	 * @param comp
	 * @return the insertion flag
	 */
	@Override
	public int create(Company comp) throws SQLException {
		String query = "INSERT INTO company (name) VALUES (?);";
		Connection con = DatabaseConnection.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, comp.getName());
		int resultcreate = stmt.executeUpdate();
		
		stmt.close();
		con.close();
		return resultcreate;
	}

	/**
	 * Update a new computer into the database
	 * @param comp
	 * @return the update flag
	 */
	@Override
	public int update(Company comp) throws SQLException {
		String query = "UPDATE company SET name= ? WHERE id = ?;";
		Connection con = DatabaseConnection.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, comp.getName());
		stmt.setLong(2, comp.getId());
		int resultUpdate = stmt.executeUpdate();
		
		stmt.close();
		con.close();
		comp = find(comp.getId()); //Note manual change are faster yet less META
		return resultUpdate;
	}

	@Override
	public void delete(Company comp) throws SQLException {
		String query = "DELETE FROM company WHERE id = ?;";
		Connection con = DatabaseConnection.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setLong(1, comp.getId());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}

	/**
	 * Wrapper fonction returning the entity
	 * @param rs : ResultSet receive by the database request
	 * @return The created object (null if ResultSet error)
	 */
	private Company wrapDatabaseResult(ResultSet rs) throws SQLException {
		Company cmp = null;
		// If result found, Company created from the Database result
		if (rs.next()) {
			cmp = new Company();
			cmp.setId(rs.getLong(1));
			cmp.setName(rs.getString(2));
		}
		return cmp;
	}

	@Override
	public List<Company> listAll() throws SQLException {
		String query = "SELECT id,name from company;";
		ResultSet results;
		List<Company> companies = new ArrayList<Company>();
		Connection con = DatabaseConnection.getInstance().getConnection();
		Statement stmt = con.createStatement();
		results = stmt.executeQuery(query);
		
		Company c;
		while((c = wrapDatabaseResult(results)) != null){
			companies.add(c);
		}
		
	    stmt.close();
	    con.close();
		return companies;
	}
}
