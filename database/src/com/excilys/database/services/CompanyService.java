package com.excilys.database.services;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Page;
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

	public Company findComputer(Long id) {
		return companyDAO.find(id);
	}

	public Company insertCompany(Company comp) {
		return companyDAO.create(comp);
	}

	public void deleteCompany(Company comp) {
		companyDAO.delete(comp);
	}

	public Page<Company> ListCompanies() {
		Page<Company> page = new Page<Company>(companyDAO.listAll());
		return page;
	}
}