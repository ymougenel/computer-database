package com.excilys.database.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page.CompanyTable;
import com.excilys.database.entities.Page.Order;
import com.excilys.database.persistence.ComputerDaoInterface;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.validators.ComputerValidator;

@Service
@Transactional
public class ComputerService implements ComputerServiceInterface {

    @Autowired
    private ComputerDaoInterface computerDAO;

    public ComputerService() {
    }

    @Override
    @Transactional(readOnly = true)
    public Computer findComputer(Long id) {
        ComputerValidator.computerIdValidation(id);
        return computerDAO.find(id);
    }

    @Override
    public Computer insertComputer(Computer comp) {
        return computerDAO.create(comp);
    }

    @Override
    public void deleteComputer(Computer comp) {
        computerDAO.delete(comp);
    }


    @Override
    public Computer updateComputer(Computer comp) {
        return computerDAO.update(comp);
    }

    @Override
    public List<Computer> listComputers(String regex, long begin, long end, CompanyTable field,
            Order order) {
        return computerDAO.listAll(regex, begin, end, field, order);
    }


    @Override
    @Transactional(readOnly = true)
    public Long countComputers() {
        return computerDAO.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countComputers(String regex) {
        return computerDAO.count(regex);
    }
}
