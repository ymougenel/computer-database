package com.excilys.database.services;

import java.util.List;

import com.excilys.database.entities.Company;

public interface CompanyServiceInterface {
    public Company findCompany(Long id);
    public Company insertCompany(Company comp);
    public Company updateCompany(Company comp);
    public void deleteCompany(Company comp);
    public List<Company> listCompanies();
    public Long countCompanies();
}
