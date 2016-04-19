package com.excilys.database.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.database.persistence.BDRequests;

public class ListCompagnies extends CommandBD {

	public ListCompagnies() {
		super();
		this.name = "Compagnies listing";
		this.shortcut ="lcs";
				
	}
	
	public ListCompagnies(String st) {
		super(st);
		this.name = "Compagnies listing";
	}
	
	@Override
	public void execute(BDRequests bdr) throws SQLException {
		System.out.println("** List compagnies called **");
		ResultSet result = bdr.query("SELECT * from company;");
		BDRequests.printfResult(result);
		//bdr.disconnect();
	}

	@Override
	public boolean optionsFit(String[] values) {
		return values[0].equals(shortcut);
	}

}
