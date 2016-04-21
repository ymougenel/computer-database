package com.excilys.database.commands;

import java.sql.SQLException;
import java.util.List;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.persistence.ComputerDAO;

public class ListComputers extends CommandBD {
	private long begin;
	private long end;
	private boolean limit;
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
	public void execute(DatabaseConnection bdr) throws SQLException {
		ComputerDAO dao = new ComputerDAO();
		List<Computer> computers;
		if (limit)
			computers = dao.listAll(begin, end);
		else
			computers = dao.listAll();
		for (Computer c : computers)
			System.out.println(c.toString());
	}

	@Override
	public boolean optionsFit(String[] values) {
		if (!values[0].equals(shortcut))
			return false;
		if (values.length == 3) {
			limit = true;
			begin = Long.parseLong(values[1]);
			end = Long.parseLong(values[2]);
		}
		else {
			limit = false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[beginIndex endIndex]";
	}


}
