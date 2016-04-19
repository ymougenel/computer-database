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
		String query = "SELECT * from computer;";
		
//		System.out.println("** List computer called **");
//		ResultSet result = bdr.query("SELECT * from computer;");
//		BDRequests.printfResult(result);
		RowSet rs = bdr.getRowSet();
		rs.setCommand(query);
		rs.execute();
        while (rs.next()) {
            System.out.println("id: " + rs.getInt(1)+"\t"+"pcname: " + rs.getString(2)+"\t"+"releasedate: " + rs.getInt(3));
        }            
        rs.close();
		
	}

	@Override
	public boolean optionsFit(String[] values) {
		return values[0].equals(shortcut);
	}


}
