package com.excilys.database.persistence;

import java.util.List;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;

public interface ComputerDaoInterface {

    /**
     * Find a computer based on the id.
     *
     * @param id
     * @return the found Computer (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Computer find(long id);

    /**
     * Find a computer based on the name.
     *
     * @param name
     * @return the found Computer (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Computer find(String name);

    /**
     * Insert a new computer into the database.
     *
     * @param comp
     * @return the created Computer
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Computer create(Computer comp);

    /**
     * Update a computer into the database.
     *
     * @param comp
     *            the computer to update
     * @return the updated company
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Computer update(Computer comp);

    /**
     * Delete a computer from the database
     *
     * @param comp
     *            the computer to delete
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public void delete(Computer comp);

    /**
     * Delete computers related to a company
     *
     * @param idCompany
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    void delete(Long idCompany);

    /**
     * List of the computers
     *
     * @return the list of all the computers
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public List<Computer> listAll();

    /**
     * List of the computers from an indexed research and a regex
     *
     * @param regex
     *            the regular expression used for the research
     * @param begin
     *            the search index start
     * @param end
     *            the search index end
     * @return the list of all the computers
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public List<Computer> listAll(String regex, long begin, long end, Page.CompanyTable field,
            Page.Order order);

    /**
     * Count the computers.
     *
     * @return number of computers
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public long count();

    /**
     * Count the computers.
     *
     * @param regex
     *            The regular expression used for the counting
     * @return number of computers
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public long count(String regex);

}
