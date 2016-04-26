package com.excilys.database.services;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.persistence.ComputerDAO;

public class ComputerService {
    private ComputerDAO computerDAO;
    private static ComputerService ComputerService;

    private ComputerService() {
        computerDAO = ComputerDAO.getInstance();
    }

    public static synchronized ComputerService getInstance() {
        if (ComputerService == null) {
            ComputerService = new ComputerService();
        }
        return ComputerService;
    }

    public Computer findComputer(Long id) {
        return computerDAO.find(id);
    }

    public Computer insertComputer(Computer comp) throws InvalidInsertionException {
        if (comp.getName() == null) {
            throw new InvalidInsertionException();
        }

        return computerDAO.create(comp);
    }

    public void deleteComputer(Computer comp) {
        computerDAO.delete(comp);
    }

    public Computer updateComputer(Computer comp) {
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