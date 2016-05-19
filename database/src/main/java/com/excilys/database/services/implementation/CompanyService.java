package com.excilys.database.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.LocalTransactionThread;
import com.excilys.database.persistence.implementation.CompanyDAO;
import com.excilys.database.persistence.implementation.ComputerDAO;
import com.excilys.database.services.CompanyServiceInterface;
import com.excilys.database.validadors.ComputerValidador;

@Service
public class CompanyService implements CompanyServiceInterface{

    //private static Logger logger = LoggerFactory.getLogger("CompanyService");

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private ComputerDAO computerDAO;

    public CompanyService() {
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
        computerDAO.delete(comp.getId());
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
