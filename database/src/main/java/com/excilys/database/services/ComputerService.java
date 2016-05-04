package com.excilys.database.services;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.persistence.ComputerDAO;
import com.excilys.database.validadors.ComputerValidador;

public enum ComputerService {

    INSTANCE;
    private ComputerDAO computerDAO;

    private ComputerService() {
        computerDAO = ComputerDAO.getInstance();
    }

    public static synchronized ComputerService getInstance() {
        return INSTANCE;
    }

    public Computer findComputer(Long id) {
        ComputerValidador.computerIdValidation(id);
        return computerDAO.find(id);
    }

    public Computer insertComputer(Computer comp) {
        ComputerValidador.computerValidation(comp);
        return computerDAO.create(comp);
    }

    public void deleteComputer(Computer comp) {
        ComputerValidador.computerValidation(comp);
        computerDAO.delete(comp);
    }

    public Computer updateComputer(Computer comp) {
        ComputerValidador.computerValidation(comp);
        return computerDAO.update(comp);
    }

    public Page<Computer> listComputers() {
        Page<Computer> page = new Page<Computer>(computerDAO.listAll());
        return page;
    }

    public Page<Computer> listComputers(String regex, long begin, long end, Page.CompanyTable field, Page.Order order) {
        Page<Computer> page = new Page<Computer>(computerDAO.listAll(regex, begin, end, field, order));
        return page;
    }

    public Long countComputers() {
        return computerDAO.count();
    }

    public Long countComputers(String regex) {
        return computerDAO.count(regex);
        //NOTE if counting code duplication, mixte in 1 method with input condition
    }
}
