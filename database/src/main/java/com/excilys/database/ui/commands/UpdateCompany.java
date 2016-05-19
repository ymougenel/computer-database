package com.excilys.database.ui.commands;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.persistence.implementation.CompanyDAO;

public class UpdateCompany extends CommandBD {
    private Long id;
    private String companyName;
    @Autowired
    private CompanyDAO companyDAO;

    public UpdateCompany() {
        this.name = "Update company";
        this.shortcut = "uc";
    }

    public UpdateCompany(String s) {
        super(s);
        this.name = "Update company";
    }

    @Override
    public void execute(DatabaseConnection bdr) throws SQLException {
        Company c = new Company();
        c.setName(companyName);
        c.setId(id);
        companyDAO.update(c);

    }

    @Override
    public boolean optionsFit(String[] values) {
        boolean correctInputs = values[0].equals(shortcut);
        if (correctInputs && values.length == 3) {
            companyName = values[1];
            id = Long.parseLong(values[2]);
            return true;
        } else {
            return false;
        }
    }

}
