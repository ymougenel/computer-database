package com.excilys.database.ui.commands;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDaoInterface;
import com.excilys.database.persistence.DatabaseConnection;

public class InsertCompany extends CommandBD {
    private String companyName;
    @Autowired
    private CompanyDaoInterface companyDAO;

    public InsertCompany() {
        this.shortcut = "icy";
        this.name ="Insert Company";
    }

    public InsertCompany(String s) {
        super(s);
        this.name ="Insert Company";
    }

    @Override
    public void execute(DatabaseConnection bdr) throws SQLException {
        Company c = new Company();
        c.setName(companyName);
        companyDAO.create(c);
    }

    @Override
    public boolean optionsFit(String[] values) {
        boolean correctInputs = values[0].equals(shortcut);
        if ( correctInputs && values.length == 2) {
            companyName = values[1];
            return true;
        } else {
            return false;
        }
    }

}
