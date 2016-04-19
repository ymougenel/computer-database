package com.excilys.database.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.database.persistence.BDRequests;

public class ShowComputerDetails extends CommandBD {
	private String field;
	private String value;
	
	public ShowComputerDetails() {
		super();
		this.name="Show computer details";
		this.shortcut="scd";
	}
	
	public ShowComputerDetails(String sct) {
		super(sct);
		this.name="Show computer details";
	}
	@Override
	public void execute(BDRequests bdr) throws SQLException {
//		String query = "SELECT id,name from computer WHERE id=?;";
//		ResultSet results = null;
//		
//		try {
//			PreparedStatement ps = con.prepareStatement(query);
//			ps.setString(1, name);
//			results = ps.executeQuery();
		ResultSet result = bdr.query("SELECT * from computer WHERE " + this.field +"= '" + this.value + "';");
		BDRequests.printfResult(result);
		//bdr.disconnect();
	}

	@Override
	public boolean optionsFit(String[] values) {
		boolean correctShortcut, correctArgv = false, correctArgs1 = false;
		correctShortcut = values[0].equals(shortcut);
		
		if (values.length >= 3 ) {
			correctArgv = true;
			
			// getting the field from values[1] (ex: "-name")
			String valueField = values[1];
			correctArgs1 = valueField.startsWith("-");
			if (correctArgs1) {
				field = valueField.substring(1, valueField.length());
			}
			
			// getting the value from values[2] (ex: "Asus")
			this.value = values[2];
			
			for (int i = 3; i< values.length ;i++)
				this.value += " " + values[i];
		}
		
		return correctShortcut && correctArgv && correctArgs1;
	}

	@Override
	public String toString() {
		return super.toString() + " -field value (ex : -id 50)";
	}

}
