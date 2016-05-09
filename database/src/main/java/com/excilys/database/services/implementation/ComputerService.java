package com.excilys.database.services.implementation;

import java.util.List;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page.CompanyTable;
import com.excilys.database.entities.Page.Order;
import com.excilys.database.persistence.implementation.ComputerDAO;
import com.excilys.database.services.ComputerServiceInterface;
import com.excilys.database.validadors.ComputerValidador;

public enum ComputerService implements ComputerServiceInterface {

    INSTANCE;
    private ComputerDAO computerDAO;

    private ComputerService() {
        computerDAO = ComputerDAO.getInstance();
    }

    public static synchronized ComputerService getInstance() {
        return INSTANCE;
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
