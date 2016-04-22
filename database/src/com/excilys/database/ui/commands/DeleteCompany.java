package com.excilys.database.ui.commands;

import java.sql.SQLException;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.persistence.CompanyDAO;

public class DeleteCompany extends CommandBD{
	private Long id;
	public DeleteCompany() {
		this.name = "Delete company";
		this.shortcut = "dc";
	}
	
	public DeleteCompany(String s) {
		super(s);
		this.name = "Delete company";
	}
	
	@Override
	public void execute(DatabaseConnection bdr) throws SQLException {
		CompanyDAO dao = CompanyDAO.getInstance();
		Company c = new Company();
		c.setId(id);
		dao.delete(c);
	}

	@Override
	public boolean optionsFit(String[] values) {
		boolean correctInputs = values[0].equals(shortcut);
		if ( correctInputs && values.length == 2) {
			id = Long.parseLong(values[1]);
			return true;
		}
		else
			return false;
	}

}
