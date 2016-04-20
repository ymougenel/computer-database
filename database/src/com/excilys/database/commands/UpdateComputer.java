package com.excilys.database.commands;

import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.BDRequests;
import com.excilys.database.persistence.ComputerDAO;

public class UpdateComputer extends CommandBD {	
private Computer computer;

public UpdateComputer() {
	this.name = "Update computer";
	this.shortcut = "ucc";
}
public UpdateComputer(String s) {
	super(s);
	this.name = "Update computer";
}
@Override
public void execute(BDRequests bdr) throws SQLException {
	ComputerDAO dao = new ComputerDAO();
	dao.update(computer);
	
}

@Override
public boolean optionsFit(String[] values) {
	if (!values[0].equals(shortcut))
		return false;
	
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
			Company c = new Company();
			c.setId(Long.parseLong(values[i+1]));
			computer.setCompany_id(c);
			break;
		default :
			System.err.println("Error No matching found for:" +values[i]);
			break;
		}
	}
	return true;
}

}