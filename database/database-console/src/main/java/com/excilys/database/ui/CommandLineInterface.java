package com.excilys.database.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;

import com.excilys.database.persistence.DAOException;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.ui.commands.CommandBD;
import com.excilys.database.ui.commands.DeleteComputer;
import com.excilys.database.ui.commands.InsertComputer;
import com.excilys.database.ui.commands.ListCompagnies;
import com.excilys.database.ui.commands.ListComputers;
import com.excilys.database.ui.commands.ShowComputerDetails;
import com.excilys.database.ui.commands.UpdateComputer;

@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class CommandLineInterface {

    // System.out.println("Prompt launch");

    protected List<CommandBD> commands;
    private BufferedReader bufferRead;
    private DatabaseConnection bdRequests;

    public CommandLineInterface() {
        bdRequests = DatabaseConnection.getInstance();
        commands = new ArrayList<CommandBD>();
    }

    public CommandLineInterface(List<CommandBD> cmds) {
        bdRequests = DatabaseConnection.getInstance();
        commands = new ArrayList<CommandBD>(cmds);
    }

    public void addCommand(CommandBD cmd) {
        commands.add(cmd);
    }

    public void launch() {

        while (true) {
            System.out.println(
                    "*********************** Commands available ********************************");
            System.out.println("\tActions \t\t\t\t Commands");
            for (CommandBD c : commands) {
                System.out.println(c.toString());
            }
            System.out.println(
                    "**************************************************************************");

            bufferRead = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.println("prompt> ");
                String line = bufferRead.readLine();
                processInput(line);
            } catch (DateTimeParseException e) {
                System.err.println("Date inputs is incorrect");
            } catch (DAOException e) {
                System.out.println("DAO EXception");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // TODO avoid null name insertion
    private void processInput(String input) throws SQLException {
        String[] values = input.split(" ");

        Iterator<CommandBD> it = commands.iterator();
        boolean foundMatchingCommand = false;

        while (!foundMatchingCommand && it.hasNext()) {
            CommandBD cmd = it.next();
            if (cmd.optionsFit(values)) {
                foundMatchingCommand = true;
                System.out.println("-> Result...");
                cmd.execute(this.bdRequests);
            }
        }
        if (!foundMatchingCommand) {
            System.out.println("No matching command found for : " + values[0]);
        }
    }

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.addCommand(new ListComputers("ll"));
        cli.addCommand(new ListCompagnies("ls"));
        cli.addCommand(new InsertComputer());
        // cli.addCommand(new InsertCompany("i"));
        cli.addCommand(new ShowComputerDetails());
        cli.addCommand(new UpdateComputer());
        // cli.addCommand(new UpdateCompany());
        cli.addCommand(new DeleteComputer());
        // cli.addCommand(new DeleteCompany());

        cli.launch();
    }
}
