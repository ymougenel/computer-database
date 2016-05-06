package com.excilys.database.services.implementation;

import java.util.List;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.LocalTransactionThread;
import com.excilys.database.persistence.implementation.CompanyDAO;
import com.excilys.database.persistence.implementation.ComputerDAO;
import com.excilys.database.services.CompanyServiceInterface;
import com.excilys.database.validadors.ComputerValidador;

public enum CompanyService implements CompanyServiceInterface{

    INSTANCE;

    //private static Logger logger = LoggerFactory.getLogger("CompanyService");

    private CompanyDAO companyDAO;

    private CompanyService() {
        companyDAO = CompanyDAO.getInstance();
    }

    public static CompanyService getInstance() {
        return INSTANCE;
    }

    @Override
    public Company findCompany(Long id) {
        ComputerValidador.computerIdValidation(id);
        return companyDAO.find(id);
    }

    @Override
    public Company insertCompany(Company comp) {
        return companyDAO.create(comp);
    }

    @Override
    public Company updateCompany(Company comp) {
        return companyDAO.update(comp);
    }

    @Override
    public void deleteCompany(Company comp) {

        LocalTransactionThread.init();
        ComputerDAO.getInstance().delete(comp.getId());
        companyDAO.delete(comp);
        LocalTransactionThread.commit();
        LocalTransactionThread.close();

    }

    @Override
    public List<Company> listCompanies() {
        return companyDAO.listAll();
    }

    @Override
    public Long countCompanies() {
        return companyDAO.count();
    }
}
