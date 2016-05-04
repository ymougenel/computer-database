package com.excilys.database.services;

import java.util.List;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDAO;
import com.excilys.database.validadors.ComputerValidador;

public enum CompanyService {

    INSTANCE;

    private CompanyDAO companyDAO;

    private CompanyService() {
        companyDAO = CompanyDAO.getInstance();
    }

    public static CompanyService getInstance() {
        return INSTANCE;
    }

    public Company findCompany(Long id) {
        ComputerValidador.computerIdValidation(id);
        return companyDAO.find(id);
    }

    public Company insertCompany(Company comp) {
        return companyDAO.create(comp);
    }

    public Company updateCompany(Company comp) {
        return companyDAO.update(comp);
    }

    public void deleteCompany(Company comp) {
        companyDAO.delete(comp);
    }

    public List<Company> listCompanies() {
        return companyDAO.listAll();
    }

    public Long countCompanies() {
        return companyDAO.count();
    }
}
