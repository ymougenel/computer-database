package com.excilys.database.services.implementation;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
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
    public Page<Computer> listComputers() {
        Page<Computer> page = new Page<Computer>(computerDAO.listAll());
        return page;
    }

    @Override
    public Page<Computer> listComputers(String regex, long begin, long end, CompanyTable field,
            Order order) {
        Page<Computer> page = new Page<Computer>(
                computerDAO.listAll(regex, begin, end, field, order));
        return page;
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
