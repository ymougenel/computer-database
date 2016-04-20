package com.excilys.database.commands;

import java.sql.SQLException;
import java.util.List;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.BDRequests;
import com.excilys.database.persistence.ComputerDAO;

public class ListComputers extends CommandBD {

	public ListComputers() {
		super();
		this.name = "Computer listing";
		this.shortcut ="lcr";
				
	}
	
	public ListComputers(String st) {
		super(st);
		this.name = "Computer listing";
	}
	
	@Override
	public void execute(BDRequests bdr) throws SQLException {
		ComputerDAO dao = new ComputerDAO();
		List<Computer> computers = dao.listAll();
		for (Computer c : computers)
			System.out.println(c.toString());
	}

	@Override
	public boolean optionsFit(String[] values) {
		return values[0].equals(shortcut);
	}


}
