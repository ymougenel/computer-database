package com.excilys.database.commands;

import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.BDRequests;
import com.excilys.database.persistence.ComputerDAO;

public class InsertComputer extends CommandBD {	
	private Computer computer;

	public InsertComputer() {
		this.name = "Insert computer";
		this.shortcut = "ic";
	}
	public InsertComputer(String s) {
		super(s);
		this.name = "Insert computer";
	}
	@Override
	public void execute(BDRequests bdr) throws SQLException {
		ComputerDAO dao = new ComputerDAO();
		dao.create(computer);
		
	}

	@Override
	public boolean optionsFit(String[] values) {
		if (!(values[0].equals(this.shortcut)))
			return false;
		System.out.println("Insertcompt launched");
		computer = new Computer();
		for (int i=1; i<values.length; i+=2) {
			switch(values[i]){
			case("-name") :
				computer.setName(values[i+1]);
				break;
			case("-id") :
				computer.setId(Long.parseLong(values[i+1]));
				break;
			case("-introduced") :
				computer.setIntroduced(LocalDate.parse(values[i+1]));
				break;
			case("-discontinued") :
				computer.setDiscontinued(LocalDate.parse(values[i+1]));
				break;
			case("-company") :
				computer.setCompany_id(Long.parseLong(values[i+1]));
				break;
			default :
				System.err.println("Error No matching found for:" +values[i]);
				break;
			}
		}
		return true;
	}

	}