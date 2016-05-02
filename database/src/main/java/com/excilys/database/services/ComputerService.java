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

    public Page<Computer> listComputers(long begin, long end) {
        Page<Computer> page = new Page<Computer>(computerDAO.listAll(begin, end));
        return page;
    }

    public Long countComputers() {
        return computerDAO.count();
    }
}
