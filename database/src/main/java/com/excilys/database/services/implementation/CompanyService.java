package com.excilys.database.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDaoInterface;
import com.excilys.database.persistence.ComputerDaoInterface;
import com.excilys.database.services.CompanyServiceInterface;
import com.excilys.database.validators.ComputerValidator;

@Service
@Transactional
public class CompanyService implements CompanyServiceInterface {

    // private static Logger logger = LoggerFactory.getLogger("CompanyService");

    @Autowired
    private CompanyDaoInterface companyDAO;

    @Autowired
    private ComputerDaoInterface computerDAO;

    public CompanyService() {
    }

    @Override
    @Transactional(readOnly = true)
    public Company findCompany(Long id) {
        ComputerValidator.computerIdValidation(id);
        return companyDAO.find(id);
    }

    @Override
    @Transactional
    public Company insertCompany(Company comp) {
        return companyDAO.create(comp);
    }

    @Override
    @Transactional
    public Company updateCompany(Company comp) {
        return companyDAO.update(comp);
    }

    @Override
    @Transactional
    public void deleteCompany(Company comp) {

        computerDAO.delete(comp.getId());
        companyDAO.delete(comp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> listCompanies() {
        return companyDAO.listAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countCompanies() {
        return companyDAO.count();
    }
}
