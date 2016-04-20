package com.excilys.database.commands;

import java.sql.SQLException;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.BDRequests;
import com.excilys.database.persistence.ComputerDAO;

public class DeleteComputer extends CommandBD {
	private Long id;

	public DeleteComputer() {
		this.name = "Delete computer";
		this.shortcut = "dcc";
	}

	public DeleteComputer(String s) {
		super(s);
		this.name = "Delete computer";
	}

	@Override
	public void execute(BDRequests bdr) throws SQLException {
		ComputerDAO dao = new ComputerDAO();
		Computer c = new Computer();
		c.setId(id);
		dao.delete(c);
	}

	@Override
	public boolean optionsFit(String[] values) {
		boolean correctInputs = values[0].equals(shortcut);
		if (correctInputs && values.length == 2) {
			id = Long.parseLong(values[1]);
			return true;
		} else
			return false;
	}

}