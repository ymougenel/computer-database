package com.excilys.database.services;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.entities.Page.CompanyTable;
import com.excilys.database.entities.Page.Order;

public interface ComputerServiceInterface {
    public Computer findComputer(Long id);
    public Computer insertComputer(Computer comp);
    public void deleteComputer(Computer comp);
    public Computer updateComputer(Computer comp);
    public Page<Computer> listComputers();
    public Page<Computer> listComputers(String regex, long begin, long end, CompanyTable field,
            Order order);
    public Long countComputers();
    public Long countComputers(String regex);

}
