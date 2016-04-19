package com.excilys.database.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

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
		String query = "SELECT * from company;";
		
//		System.out.println("** List computer called **");
//		ResultSet result = bdr.query("SELECT * from computer;");
//		BDRequests.printfResult(result);
		RowSet rs = bdr.getRowSet();
		rs.setCommand(query);
		rs.execute();
        while (rs.next()) {
            System.out.println("id: " + rs.getInt(1) +"\t"+"pcname: " + rs.getString(2));
        }            
        rs.close();
	}

	@Override
	public boolean optionsFit(String[] values) {
		return values[0].equals(shortcut);
	}

}
