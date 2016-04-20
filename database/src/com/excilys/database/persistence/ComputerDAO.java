package com.excilys.database.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.RowSet;

import com.excilys.database.entities.Computer;

public class ComputerDAO extends DAO<Computer>{

	@Override
	public Computer find(long id) throws SQLException {
		Computer cmp = null;
		String query = "SELECT * from computer WHERE id = ?;";
		//System.out.println("### +i query called for : "+query +" << "+id);
		RowSet rs = BDRequests.getInstance().getRowSet();
		rs.setCommand(query);
		rs.setLong(1, id);
	
		rs.execute();
		cmp = wrapDatabaseResult(rs);
        rs.close();
		return cmp;
	}

	@Override
	public Computer find(String name) throws SQLException {
		Computer cmp = null;
		String query = "SELECT * from computer WHERE name = ?;";
		//System.out.println("### +i query called for : "+query +" << "+name);
		RowSet rs = BDRequests.getInstance().getRowSet();
		rs.setCommand(query);
		rs.setString(1, name);
	
		rs.execute();
		cmp = wrapDatabaseResult(rs);          
        rs.close();
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

	
private Computer wrapDatabaseResult(RowSet rs) throws SQLException {
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
}
