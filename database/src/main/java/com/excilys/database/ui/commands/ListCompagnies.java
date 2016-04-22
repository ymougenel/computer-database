package com.excilys.database.ui.commands;

import java.sql.SQLException;
import java.util.List;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.persistence.CompanyDAO;

public class ListCompagnies extends CommandBD {

	public ListCompagnies() {
		super();
		this.name = "Compagnies listing";
		this.shortcut = "lcs";

	}

	public ListCompagnies(String st) {
		super(st);
		this.name = "Compagnies listing";
	}

	@Override
	public void execute(DatabaseConnection bdr) throws SQLException {
		CompanyDAO dao = CompanyDAO.getInstance();
		List<Company> companies = dao.listAll();
		for (Company c : companies)
			System.out.println(c.toString());
	}

	@Override
	public boolean optionsFit(String[] values) {
		return values[0].equals(shortcut);
	}

}
