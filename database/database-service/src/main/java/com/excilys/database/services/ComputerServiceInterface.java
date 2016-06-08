package com.excilys.database.services;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page.CompanyTable;
import com.excilys.database.entities.Page.Order;

import java.util.List;

public interface ComputerServiceInterface {
    public Computer findComputer(Long id);
    public Computer insertComputer(Computer comp);
    public void deleteComputer(Computer comp);
    public Computer updateComputer(Computer comp);
    public List<Computer> listComputers(String regex, long begin, long end, CompanyTable field,
            Order order);
    public Long countComputers();
    public Long countComputers(String regex);

}
