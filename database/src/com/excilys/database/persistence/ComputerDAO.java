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

public class ComputerDAO extends DAO<Computer>{

	@Override
	public Computer find(long id) throws SQLException {
		Computer cmp;
		String query = "select c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name from computer c left join company o on c.company_id = o.id WHERE c.id = ?;";
		ResultSet results;
		// System.out.println("### +i query called for : "+query +" << "+name);
		Connection con = BDRequests.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setLong(1, id);
		results = stmt.executeQuery();
		cmp = wrapDatabaseResult(results);
		
		stmt.close();
		con.close();
		return cmp;
	}

	@Override
	public Computer find(String name) throws SQLException {
		Computer cmp;
		String query = "select c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name from computer c left join company o on c.company_id = o.id WHERE c.name = ?;";
		ResultSet results;
		// System.out.println("### +i query called for : "+query +" << "+name);
		Connection con = BDRequests.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, name);
		results = stmt.executeQuery();
		cmp = wrapDatabaseResult(results);
		
		stmt.close();
		con.close();
		return cmp;
	}

	@Override
	public int create(Computer comp) throws SQLException{
		String query = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
		Connection con = BDRequests.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, comp.getName());
		
		if (comp.getIntroduced() != null ) {
			stmt.setDate(2, java.sql.Date.valueOf(comp.getIntroduced()));
		}
		else {
			stmt.setNull(2, java.sql.Types.DATE);
		}
		
		if (comp.getDiscontinued() != null ) {
			stmt.setDate(3, java.sql.Date.valueOf(comp.getDiscontinued()));
		}
		else {
			stmt.setNull(3, java.sql.Types.DATE);
		}
		
		if (comp.getCompany_id() != null) {
			stmt.setLong(4, comp.getCompany_id().getId());
		}
		else {
			stmt.setNull(4, java.sql.Types.INTEGER);
		}
		
		int resultUpdate = stmt.executeUpdate();
		
		stmt.close();
		con.close();
		return resultUpdate;
	}

	@Override
	public int update(Computer comp) throws SQLException{
		String query = "UPDATE computer SET name= ?, introduced= ?, discontinued = ?, company_id = ? WHERE id = ?;";
		Connection con = BDRequests.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, comp.getName());
		
		if (comp.getIntroduced() != null ) {
			stmt.setDate(2, java.sql.Date.valueOf(comp.getIntroduced()));
		}
		else {
			stmt.setNull(2, java.sql.Types.DATE);
		}
		
		if (comp.getDiscontinued() != null ) {
			stmt.setDate(3, java.sql.Date.valueOf(comp.getDiscontinued()));
		}
		else {
			stmt.setNull(3, java.sql.Types.DATE);
		}
		
		if (comp.getCompany_id() != null) {
			stmt.setLong(4, comp.getCompany_id().getId());
		}
		
		else {
			stmt.setNull(4, java.sql.Types.INTEGER);
		}
		
		stmt.setLong(5, comp.getId());
		int resExecution = stmt.executeUpdate();
		
		stmt.close();
		con.close();
		comp = find(comp.getId()); //Note manual change are faster yet less META
		return resExecution;
	}

	@Override
	public void delete(Computer comp) throws SQLException{
		String query = "DELETE FROM computer WHERE id = ?;";
		Connection con = BDRequests.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setLong(1, comp.getId());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}

	
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
public List<Computer> listAll() throws SQLException {
	String query = "select c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name from computer c left join company o on c.company_id = o.id;";
	ResultSet results;
	List<Computer> computers = new ArrayList<Computer>();
	Connection con = BDRequests.getInstance().getConnection();
	Statement stmt = con.createStatement();
	results = stmt.executeQuery(query);
	
	Computer c;
	while((c = wrapDatabaseResult(results)) != null){
		computers.add(c);
	}
	
    stmt.close();
    con.close();
	return computers;
}

public List<Computer> listAll(long begin, long end) throws SQLException {
	String query = "select c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name from computer c left join company o on c.company_id = o.id LIMIT ?,?;";
	ResultSet results;
	List<Computer> computers = new ArrayList<Computer>();
	Connection con = BDRequests.getInstance().getConnection();
	PreparedStatement stmt = con.prepareStatement(query);
	stmt.setLong(1, begin);
	stmt.setLong(2, end);
	results = stmt.executeQuery();
	
	Computer c;
	while((c = wrapDatabaseResult(results)) != null){
		computers.add(c);
	}
	
    stmt.close();
    con.close();
	return computers;
}
}
