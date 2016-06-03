package com.excilys.database.ui.commands;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDaoInterface;
import com.excilys.database.persistence.DatabaseConnection;

@Component
public class DeleteCompany extends CommandBD {
    private Long id;

    @Autowired
    CompanyDaoInterface companyDAO;

    public DeleteCompany() {
        this.name = "Delete company";
        this.shortcut = "dc";
    }

    public DeleteCompany(String s) {
        super(s);
        this.name = "Delete company";
    }

    @Override
    public void execute(DatabaseConnection bdr) throws SQLException {
        Company c = new Company();
        c.setId(id);
        companyDAO.delete(c);
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
