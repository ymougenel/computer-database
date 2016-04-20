package com.excilys.database.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.excilys.database.commands.CommandBD;
import com.excilys.database.commands.DeleteCompany;
import com.excilys.database.commands.InsertCompany;
import com.excilys.database.commands.ListCompagnies;
import com.excilys.database.commands.ListComputers;
import com.excilys.database.commands.ShowComputerDetails;
import com.excilys.database.commands.UpdateCompany;
import com.excilys.database.persistence.BDRequests;

public class CommandLineInterface {

	//System.out.println("Prompt launch");
	
	protected List<CommandBD> commands;
	private BufferedReader bufferRead;
	private BDRequests bdRequests;
	
	public CommandLineInterface() {
		bdRequests = BDRequests.getInstance();
		commands = new ArrayList<CommandBD>();
	}
	
	public CommandLineInterface(List<CommandBD> cmds) {
		bdRequests = BDRequests.getInstance();
		commands = new ArrayList<CommandBD>(cmds);
	}
	
	public void addCommand(CommandBD cmd) {
		commands.add(cmd);
	}
	
	public void launch(){

		while(true) {
			System.out.println("*********************** Commands available ********************************");
			System.out.println("\tActions \t\t\t\t Commands");
			for (CommandBD c : commands)
				System.out.println(c.toString());
			System.out.println("**************************************************************************");
			
	        bufferRead = new BufferedReader(new InputStreamReader(System.in));
	        try {
	        	System.out.println("prompt> ");
				String line = bufferRead.readLine();
				processInput(line);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processInput(String input) throws SQLException {
		String[] values = input.split(" ");
		
//		System.out.println("--------------------");
//		for (int i=0;i<values.length;i++) {
//			System.out.println("Values["+i+"] = "+values[i]);
//		}
//		System.out.println("--------------------");

		Iterator<CommandBD> it =commands.iterator();
		boolean foundMatchingCommand = false;
		
		while (!foundMatchingCommand && it.hasNext()) {
			CommandBD cmd = it.next();
			if (cmd.optionsFit(values)) {
				foundMatchingCommand = true;
				System.out.println("-> Result...");
				cmd.execute(this.bdRequests);
			}
		}
		if (!foundMatchingCommand)
			System.out.println("No matching command found for : "+values[0]);
	}
	public static void main(String[] args) {
		CommandLineInterface cli = new CommandLineInterface();
		cli.addCommand(new ListComputers("ll"));
		cli.addCommand(new ListCompagnies("ls"));
		cli.addCommand(new InsertCompany("i"));
		cli.addCommand(new ShowComputerDetails());
		cli.addCommand(new UpdateCompany());
		cli.addCommand(new DeleteCompany());
		cli.launch();
	}
}
