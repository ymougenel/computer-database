package com.excilys.database.services;

import java.util.List;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDAO;

public class CompanyService {
    private CompanyDAO companyDAO;
    private static CompanyService companyService;

    private CompanyService() {
        companyDAO = CompanyDAO.getInstance();
    }

    public static synchronized CompanyService getInstance() {
        if (companyService == null) {
            companyService = new CompanyService();
        }
        return companyService;
    }

    public Company findCompany(Long id) {
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

    public List<Company> ListCompanies() {
        return companyDAO.listAll();
    }

    public Long countCompanies() {
        return companyDAO.count();
    }
}
