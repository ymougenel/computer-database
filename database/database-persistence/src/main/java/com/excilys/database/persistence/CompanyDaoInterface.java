package com.excilys.database.persistence;

import java.util.List;

import com.excilys.database.entities.Company;

public interface CompanyDaoInterface {

    /**
     * Find a company based on the id.
     *
     * @param id
     *            id to be found
     * @return the found company (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Company find(Long id);

    /**
     * Find a company based on the name.
     *
     * @param name
     * @return the found Company (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Company find(String name);

    /**
     * Insert a new company into the database.
     *
     * @param comp
     * @return the created company
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Company create(Company comp);

    /**
     * Update a company into the database.
     *
     * @param comp
     *            the company to update
     * @return the updated company
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public Company update(Company comp);

    /**
     * Delete a company from the database.
     *
     * @param comp
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public void delete(Company comp);

    /**
     * List of the companies.
     *
     * @return the list of all the companies
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public List<Company> listAll();

    /**
     * Count the companies.
     *
     * @return number of companies
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public long count();
}
