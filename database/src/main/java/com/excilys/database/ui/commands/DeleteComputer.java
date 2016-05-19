package com.excilys.database.ui.commands;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.database.entities.Computer;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.persistence.implementation.ComputerDAO;

public class DeleteComputer extends CommandBD {
    private Long id;
    @Autowired
    ComputerDAO computerDAO;

    public DeleteComputer() {
        this.name = "Delete computer";
        this.shortcut = "dcc";
    }

    public DeleteComputer(String s) {
        super(s);
        this.name = "Delete computer";
    }

    @Override
    public void execute(DatabaseConnection bdr) throws SQLException {
        Computer c = new Computer();
        c.setId(id);
        computerDAO.delete(c);
    }

    @Override
    public boolean optionsFit(String[] values) {
        boolean correctInputs = values[0].equals(shortcut);
        if (correctInputs && values.length == 2) {
            id = Long.parseLong(values[1]);
            return true;
        } else {
            return false;
        }
    }

}