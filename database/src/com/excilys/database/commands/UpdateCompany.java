package com.excilys.database.commands;

import java.sql.SQLException;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.BDRequests;
import com.excilys.database.persistence.CompanyDAO;

public class UpdateCompany extends CommandBD{
	private Long id;
	private String companyName;
	
	public UpdateCompany() {
		this.name = "Update company";
		this.shortcut = "uc";
	}
	public UpdateCompany(String s) {
		super(s);
		this.name = "Update company";
	}
	@Override
	public void execute(BDRequests bdr) throws SQLException {
		CompanyDAO dao = new CompanyDAO();
		Company c = new Company();
		c.setName(companyName);
		c.setId(id);
		dao.update(c);
		
	}

	@Override
	public boolean optionsFit(String[] values) {
		boolean correctInputs = values[0].equals(shortcut);
		if ( correctInputs && values.length == 3) {
			companyName = values[1];
			id = Long.parseLong(values[2]);
			return true;
		}
		else
			return false;
	}

}
