package com.excilys.database.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.excilys.database.persistence.BDRequests;

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
		System.out.println("** List computer called **");
		ResultSet result = bdr.query("SELECT * from computer;");
		BDRequests.printfResult(result);
		RowSet rs = bdr.getRowSet();
	}

	@Override
	public boolean optionsFit(String[] values) {
		return values[0].equals(shortcut);
	}


}
