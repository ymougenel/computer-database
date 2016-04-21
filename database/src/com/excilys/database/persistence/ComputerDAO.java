package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;

public class ComputerDAO extends DAO<Computer> {
	private static final String FIND_ID = 	"SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.id = ?;";
	private static final String FIND_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.name = ?;";
	private static final String CREATE = 	"INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE = 	"UPDATE computer SET name= ?, introduced= ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private static final String DELETE = 	"DELETE FROM computer WHERE id = ?;";
	private static final String LISTALL = 	"SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id;";
	private static final String LISTALL_INDEX = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id LIMIT ?,?;";

	@Override
	public Computer find(long id) {
		Computer cmp = null;
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
	public Computer find(String name) {
		Computer cmp;
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
		} finally {
			try {
				con.close();
				results.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return cmp;
	}

	@Override
	public Computer create(Computer comp) {
		Connection con = null;
		ResultSet generatedKeys = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, comp.getName());

			if (comp.getIntroduced() != null) {
				stmt.setDate(2, java.sql.Date.valueOf(comp.getIntroduced()));
			} else {
				stmt.setNull(2, java.sql.Types.DATE);
			}

			if (comp.getDiscontinued() != null) {
				stmt.setDate(3, java.sql.Date.valueOf(comp.getDiscontinued()));
			} else {
				stmt.setNull(3, java.sql.Types.DATE);
			}

			if (comp.getCompany_id() != null) {
				stmt.setLong(4, comp.getCompany_id().getId());
			} else {
				stmt.setNull(4, java.sql.Types.INTEGER);
			}

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
		} finally {
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

	@Override
	public Computer update(Computer comp) {
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(UPDATE);
			stmt.setString(1, comp.getName());

			if (comp.getIntroduced() != null) {
				stmt.setDate(2, java.sql.Date.valueOf(comp.getIntroduced()));
			} else {
				stmt.setNull(2, java.sql.Types.DATE);
			}

			if (comp.getDiscontinued() != null) {
				stmt.setDate(3, java.sql.Date.valueOf(comp.getDiscontinued()));
			} else {
				stmt.setNull(3, java.sql.Types.DATE);
			}

			if (comp.getCompany_id() != null) {
				stmt.setLong(4, comp.getCompany_id().getId());
			}

			else {
				stmt.setNull(4, java.sql.Types.INTEGER);
			}

			stmt.setLong(5, comp.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return comp;
	}

	@Override
	public void delete(Computer comp) {
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(DELETE);
			stmt.setLong(1, comp.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
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
	private Computer wrapDatabaseResult(ResultSet rs) throws SQLException {
		Computer cmp = null;
		// If result found, Computer created from the Database result
		if (rs.next()) {
			cmp = new Computer();
			cmp.setId(rs.getLong(1));
			cmp.setName(rs.getString(2));
			Timestamp dateIntroduced = rs.getTimestamp(3);
			if (dateIntroduced != null)
				cmp.setIntroduced(dateIntroduced.toLocalDateTime().toLocalDate());
			Timestamp datediscontinued = rs.getTimestamp(4);
			if (datediscontinued != null)
				cmp.setDiscontinued(datediscontinued.toLocalDateTime().toLocalDate());
			Company company = null;
			Long company_id = rs.getLong(5);
			if (company_id != null) {
				company = new Company();
				company.setId(company_id);
				String company_name = rs.getString(6);
				company.setName(company_name);
			}
			cmp.setCompany_id(company);
		}
		return cmp;
	}

	@Override
	public List<Computer> listAll() {
		ResultSet results = null;
		List<Computer> computers = new ArrayList<Computer>();
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(LISTALL);

			Computer c;
			while ((c = wrapDatabaseResult(results)) != null) {
				computers.add(c);
			}
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
		return computers;
	}

	public List<Computer> listAll(long begin, long end) {
		ResultSet results = null;
		List<Computer> computers = new ArrayList<Computer>();
		Connection con = null;
		try {
			con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(LISTALL_INDEX);
			stmt.setLong(1, begin);
			stmt.setLong(2, end);
			results = stmt.executeQuery();

			Computer c;
			while ((c = wrapDatabaseResult(results)) != null) {
				computers.add(c);
			}

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
		return computers;
	}
}
