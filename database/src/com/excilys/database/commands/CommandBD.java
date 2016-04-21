package com.excilys.database.commands;

import java.sql.SQLException;

import com.excilys.database.persistence.DatabaseConnection;

/** 
 * Abstract command class
 * @author Yann Mougenel
 *
 */
public abstract class CommandBD {

	protected String name;
	protected String shortcut;
	
	public CommandBD()  {}
	
	public CommandBD( String shortcurt) {
		this.shortcut = shortcurt;
	}
	public abstract void execute(DatabaseConnection bdr)  throws SQLException;
	
	public String getShorcut() { 
		return shortcut;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract boolean optionsFit(String[] values);
	
	public String toString() {
		return this.name +": \t  Called with \t->\t "+this.shortcut;
	}
}
