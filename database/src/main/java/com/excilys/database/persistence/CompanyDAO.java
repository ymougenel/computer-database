package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.database.entities.Company;

/**
 * Company DAO (Singleton) Provides CRUD company database methods : Create, Retrieve, Update, Delete
 *
 * @author Yann Mougenel
 *
 */
public enum CompanyDAO implements DAO<Company> {

    INSTANCE;

    private static final String FIND_ID = "SELECT id, name from company WHERE id = ?;";
    private static final String FIND_NAME = "SELECT id, name from company WHERE name = ?;";
    private static final String CREATE = "INSERT INTO company (name) VALUES (?);";
    private static final String UPDATE = "UPDATE company SET name= ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM company WHERE id = ?;";
    private static final String LISTALL = "SELECT id,name from company;";
    private static final String COUNT = "SELECT COUNT(*) FROM company;";
    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    private CompanyDAO() {
    }

    // NOTE optimization possible : synchronized -> if (dao== null) {
    // synchronized if (dao == null)
    public static synchronized CompanyDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Find a company based on the id.
     *
     * @param id
     *            id to be found
     * @return the found company (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Company find(long id) {
        logger.info("FIND_ID" + " << " + id);
        Company cmp;
        ResultSet results = null;
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(FIND_ID);
            stmt.setLong(1, id);
            results = stmt.executeQuery();
            cmp = wrapDatabaseResult(results);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                results.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cmp;
    }

    /**
     * Find a company based on the name.
     *
     * @param name
     * @return the found Company (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Company find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));
        Company cmp;
        ResultSet results = null;
        // System.out.println("### +i query called for : "+query +" << "+name);
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(FIND_NAME);
            stmt.setString(1, name);
            results = stmt.executeQuery();
            cmp = wrapDatabaseResult(results);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                con.close();
                results.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cmp;
    }

    /**
     * Insert a new company into the database.
     *
     * @param comp
     * @return the created company
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Company create(Company comp) {
        logger.info("CREATE" + " << " + comp.toString());
        ResultSet generatedKeys = null;
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, comp.getName());
            stmt.executeUpdate();

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                comp.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return comp;
    }

    /**
     * Update a company into the database.
     *
     * @param comp
     *            the company to update
     * @return the updated company
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Company update(Company comp) {
        logger.info("UPDATE" + " << " + comp.toString());
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE);
            stmt.setString(1, comp.getName());
            stmt.setLong(2, comp.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return comp;
    }

    /**
     * Delete a company from the database.
     *
     * @param comp
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public void delete(Company comp) {
        logger.info("DELETE" + " << " + comp.toString());
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(DELETE);
            stmt.setLong(1, comp.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Wrapper function returning the entity.
     *
     * @param rs
     *            : ResultSet receive by the database request
     * @return The created object (null if ResultSet error)
     */
    private Company wrapDatabaseResult(ResultSet rs) throws SQLException {
        Company cmp = null;
        // If result found, Company created from the Database result
        if (rs.next()) {
            cmp = new Company();
            cmp.setId(rs.getLong("id"));
            cmp.setName(rs.getString("name"));
        }
        return cmp;
    }

    /**
     * List of the companies.
     *
     * @return the list of all the companies
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public List<Company> listAll() {
        logger.info("LISTALL");
        ResultSet results = null;
        List<Company> companies = new ArrayList<Company>();
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            Statement stmt = con.createStatement();
            results = stmt.executeQuery(LISTALL);

            Company c;
            while ((c = wrapDatabaseResult(results)) != null) {
                companies.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                con.close();
                results.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return companies;
    }

    /**
     * Count the companies.
     *
     * @return number of companies
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public long count() {
        logger.info("COUNT");
        ResultSet results = null;
        long count = 0;
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            Statement stmt = con.createStatement();
            results = stmt.executeQuery(COUNT);

            if (results.next()) {
                count = results.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                results.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return count;
    }
}
