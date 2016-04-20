package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.RowSet;
import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;

public class ComputerDAO extends DAO<Computer>{

	@Override
	public Computer find(long id) throws SQLException {
		Computer cmp;
		String query = "SELECT * from computer WHERE id = ?;";
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
		String query = "SELECT * from computer WHERE name = ?;";
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
	public Computer create(Computer comp) throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Computer update(Computer comp) throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Computer comp) throws SQLException{
		// TODO Auto-generated method stub
		
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
			Long company = rs.getLong(4);
			if (company != null)
				cmp.setCompany_id(company);
		}
		return cmp;
	}

@Override
public List<Computer> listAll() throws SQLException {
	String query = "SELECT * from computer;";
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
}
