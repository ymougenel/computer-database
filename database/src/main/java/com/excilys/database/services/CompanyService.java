package com.excilys.database.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDAO;
import com.excilys.database.persistence.ComputerDAO;
import com.excilys.database.persistence.DAOException;
import com.excilys.database.persistence.DatabaseConnection;
import com.excilys.database.validadors.ComputerValidador;

public enum CompanyService {

    INSTANCE;

    private static Logger logger = LoggerFactory.getLogger("CompanyService");

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

        logger.info("DELETE" + " << " + comp.toString());

        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            ComputerDAO.getInstance().delete(con, comp.getId());
            companyDAO.delete(con, comp);
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException e1) {
                logger.error(e.getMessage());
                e1.printStackTrace();
                throw new DAOException(e);
            }

            throw new DAOException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new DAOException(e);

            }
        }
    }

    public List<Company> listCompanies() {
        return companyDAO.listAll();
    }

    public Long countCompanies() {
        return companyDAO.count();
    }
}
