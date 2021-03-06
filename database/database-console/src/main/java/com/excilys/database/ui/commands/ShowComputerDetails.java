package com.excilys.database.ui.commands;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.ComputerDaoInterface;
import com.excilys.database.persistence.DatabaseConnection;

public class ShowComputerDetails extends CommandBD {
    private String field;
    private String value;
    @Autowired
    ComputerDaoInterface computerDAO;

    public ShowComputerDetails() {
        super();
        this.name = "Show computer details";
        this.shortcut = "scd";
    }

    public ShowComputerDetails(String sct) {
        super(sct);
        this.name = "Show computer details";
    }

    @Override
    public void execute(DatabaseConnection bdr) throws SQLException {
        Computer comp;
        if (field.equals("name")) {
            comp = computerDAO.find(this.value);
        } else if (field.equals("id")) {
            comp = computerDAO.find(Long.parseLong(value));
        } else {
            throw new SQLException();
        }
        System.out.println("+" + (comp == null));
        if (comp != null) {
            System.out.println(comp.toString());
        } else {
            System.out.println("Computer null!");
        }
    }

    @Override
    public boolean optionsFit(String[] values) {
        boolean correctShortcut, correctArgv = false, correctArgs1 = false;
        correctShortcut = values[0].equals(shortcut);

        if (values.length >= 3) {
            correctArgv = true;

            // getting the field from values[1] (ex: "-name")
            String valueField = values[1];
            correctArgs1 = valueField.startsWith("-");
            if (correctArgs1) {
                field = valueField.substring(1, valueField.length());
            }

            // getting the value from values[2] (ex: "Asus")
            this.value = values[2];

            for (int i = 3; i < values.length; i++) {
                this.value += " " + values[i];
            }
        }

        return correctShortcut && correctArgv && correctArgs1;
    }

    @Override
    public String toString() {
        return super.toString() + " -field value (ex : -id 50)";
    }

}
