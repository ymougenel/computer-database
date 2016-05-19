package com.excilys.database.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page.CompanyTable;
import com.excilys.database.entities.Page.Order;
import com.excilys.database.persistence.implementation.ComputerDAO;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.validadors.ComputerValidador;

@Service
public class ComputerService implements ComputerServiceInterface {

    @Autowired
    private ComputerDAO computerDAO;

    public ComputerService() {
    }

    @Override
    public Computer findComputer(Long id) {
        ComputerValidador.computerIdValidation(id);
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
    public Long countComputers() {
        return computerDAO.count();
    }

    @Override
    public Long countComputers(String regex) {
        return computerDAO.count(regex);
        // NOTE if counting code duplication, mixte in 1 method with input condition
    }
}
