package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.database.entities.Company;

/**
 * Company DAO (Singleton)
 * Contains CRUD company database methods : Create, Retrieve, Update, Delete
 * @author Yann Mougenel
 *
 */
public class CompanyDAO extends DAO<Company> {
	private static final String FIND_ID = "SELECT id, name from company WHERE id = ?;";
	private static final String FIND_NAME = "SELECT id, name from company WHERE name = ?;";
	private static final String CREATE = "INSERT INTO company (name) VALUES (?);";
	private static final String UPDATE = "UPDATE company SET name= ? WHERE id = ?;";
	private static final String DELETE = "DELETE FROM company WHERE id = ?;";
	private static final String LISTALL = "SELECT id,name from company;";
	private static CompanyDAO companyDAO;
	
	private CompanyDAO() {}
	
	// NOTE optimization possible : synchronized -> if (dao== null) { synchronized if (dao == null)
	public static synchronized CompanyDAO getInstance() {
		if (companyDAO == null) {
			companyDAO = new CompanyDAO();
		}
		return companyDAO;
	}
	
	@Override
	public Company find(long id) {
		Company cmp;
		ResultSet results = null;
		// System.out.println("### +i query called for : "+query +" << "+name);
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(FIND_ID);
			stmt.setLong(1, id);
			results = stmt.executeQuery();
			cmp = wrapDatabaseResult(results);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			try {
				results.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cmp;
	}

	@Override
	public Company find(String name){
		Company cmp;
		ResultSet results = null;
		// System.out.println("### +i query called for : "+query +" << "+name);
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(FIND_NAME);
			stmt.setString(1, name);
			results = stmt.executeQuery();
			cmp = wrapDatabaseResult(results);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}
		finally {
			try {
				con.close();
				results.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cmp;
	}

	/**
	 * Insert a new computer into the database
	 * 
	 * @param comp
	 * @return the insertion flag
	 */
	@Override
	public Company create(Company comp) {
		ResultSet generatedKeys = null;
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, comp.getName());
			stmt.executeUpdate();
			
			generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				comp.setId(generatedKeys.getLong(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}
		finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return comp;
	}

	/**
	 * Update a new computer into the database
	 * 
	 * @param comp
	 * @return the update flag
	 */
	@Override
	public Company update(Company comp) {
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(UPDATE);
			stmt.setString(1, comp.getName());
			stmt.setLong(2, comp.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return comp;
	}

	@Override
	public void delete(Company comp) {
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(DELETE);
			stmt.setLong(1, comp.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Wrapper function returning the entity
	 * 
	 * @param rs
	 *            : ResultSet receive by the database request
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
	public List<Company> listAll() {
		ResultSet results = null;
		List<Company> companies = new ArrayList<Company>();
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(LISTALL);

			Company c;
			while ((c = wrapDatabaseResult(results)) != null) {
				companies.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		}
		finally {
			try {
				con.close();
				results.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return companies;
	}
}
