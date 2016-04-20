package com.excilys.database.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import com.excilys.database.entities.Company;

public class CompanyDAO extends DAO<Company> {

	@Override
	public Company find(long id) throws SQLException {
		Company cmp = null;
		String query = "SELECT * from Company WHERE id = ?;";
		// System.out.println("### +i query called for : "+query +" << "+id);
		RowSet rs = BDRequests.getInstance().getRowSet();
		rs.setCommand(query);
		rs.setLong(1, id);

		rs.execute();
		cmp = wrapDatabaseResult(rs);
		rs.close();
		return cmp;
	}

	@Override
	public Company find(String name) throws SQLException {
		Company cmp = null;
		String query = "SELECT * from company WHERE name = ?;";
		// System.out.println("### +i query called for : "+query +" << "+name);
		RowSet rs = BDRequests.getInstance().getRowSet();
		rs.setCommand(query);
		rs.setString(1, name);

		rs.execute();
		cmp = wrapDatabaseResult(rs);
		rs.close();
		return cmp;
	}

	@Override
	public Company create(Company comp) throws SQLException {
		RowSet rs = BDRequests.getInstance().getRowSet();
		rs.moveToInsertRow();
		rs.updateString("name", comp.getName());
		rs.insertRow();
		rs.close();
		return comp;
	}

	@Override
	public Company update(Company comp) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Company comp) throws SQLException {
		// TODO Auto-generated method stub

	}

	private Company wrapDatabaseResult(RowSet rs) throws SQLException {
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
		String query = "SELECT * from company;";
		List<Company> companys = new ArrayList<Company>();
		RowSet rs = BDRequests.getInstance().getRowSet();
		rs.setCommand(query);
		rs.execute();

		Company c;
		while ((c = wrapDatabaseResult(rs)) != null) {
			companys.add(c);
		}
		rs.close();
		return companys;
	}
}
