package com.excilys.database.ui.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.persistence.ComputerDAO;
import com.excilys.database.persistence.DatabaseConnection;

public class ListComputers extends CommandBD {
	private long begin;
	private boolean limit;
	private static int pageSize = 20;

	public ListComputers() {
		super();
		this.name = "Computer listing";
		this.shortcut = "lcr";

	}

	public ListComputers(String st) {
		super(st);
		this.name = "Computer listing";
	}

	@Override
	public void execute(DatabaseConnection bdr) throws SQLException {
		ComputerDAO dao = ComputerDAO.getInstance();
		Page<Computer> page;
		// List<Computer> computers;
		if (limit)
			page = new Page<>(dao.listAll(begin, pageSize), pageSize);
		else
			page = new Page<>(dao.listAll(), pageSize);
		page.printf();

		if (limit) {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("<1 - 2>  q = quit");
			String input;
			try {
				while (!(input = bufferRead.readLine()).equals("q")) {
					if (input.equals("1")) {
						begin -= pageSize;
					} else if (input.equals("2")) {
						begin += pageSize;
					}
					page = new Page<>(dao.listAll(begin, pageSize), pageSize);
					page.printf();
					System.out.println("<1 - 2> q3");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean optionsFit(String[] values) {
		if (!values[0].equals(shortcut))
			return false;
		if (values.length == 2) {
			limit = true;
			begin = Long.parseLong(values[1]);
		} else {
			limit = false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + " [beginIndex]";
	}

}
