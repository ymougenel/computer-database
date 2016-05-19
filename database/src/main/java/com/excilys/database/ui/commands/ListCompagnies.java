package com.excilys.database.ui.commands;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.persistence.implementation.CompanyDAO;

public class ListCompagnies extends CommandBD {

    @Autowired
    CompanyDAO companyDAO;

    public ListCompagnies() {
        super();
        this.name = "Compagnies listing";
        this.shortcut = "lcs";

    }

    public ListCompagnies(String st) {
        super(st);
        this.name = "Compagnies listing";
    }

    @Override
    public void execute(DatabaseConnection bdr) throws SQLException {
        List<Company> companies = companyDAO.listAll();
        for (Company c : companies) {
            System.out.println(c.toString());
        }
    }

    @Override
    public boolean optionsFit(String[] values) {
        return values[0].equals(shortcut);
    }

}
