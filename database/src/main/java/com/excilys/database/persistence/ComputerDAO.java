package com.excilys.database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;

/**
 * Computer DAO (Singleton) Provides CRUD computer database methods : Create, Retrieve, Update,
 * Delete
 *
 * @author Yann Mougenel
 *
 */
public enum ComputerDAO implements DAO<Computer> {

    INSTANCE;

    private static final String FIND_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.id = ?;";
    private static final String FIND_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.name = ?;";
    private static final String CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
    private static final String UPDATE = "UPDATE computer SET name= ?, introduced= ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM computer WHERE id = ?;";
    private static final String LISTALL = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id;";
    private static final String LISTALL_INDEX = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id LIMIT ?,?;";
    private static final String LISTALL_INDEX_REGEX = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.name LIKE ? LIMIT ?,?;";
    private static final String COUNT = "SELECT COUNT(*) FROM computer;";
    private static final String COUNT_REGEX = "SELECT COUNT(*) FROM computer WHERE name LIKE ?;";

    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    private ComputerDAO() {
    }

    public static ComputerDAO getInstance() {
        return INSTANCE;
    }

    /**
     * Find a computer based on the id.
     *
     * @param id
     * @return the found Computer (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Computer find(long id) {
        logger.info("FIND_ID" + " << " + id);
        Computer cmp = null;
        ResultSet results = null;
        // System.out.println("### +i query called for : "+query +" << "+name);
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(FIND_ID);
            stmt.setLong(1, id);
            results = stmt.executeQuery();
            cmp = wrapDatabaseResult(results);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
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
     * Find a computer based on the name.
     *
     * @param name
     * @return the found Computer (NULL if not found)
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Computer find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));
        Computer cmp;
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
     * Insert a new computer into the database.
     *
     * @param comp
     * @return the created Computer
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Computer create(Computer comp) {
        logger.info("CREATE" + " << " + comp.toString());
        Connection con = null;
        ResultSet generatedKeys = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, comp.getName());

            if (comp.getIntroduced() != null) {
                stmt.setDate(2, java.sql.Date.valueOf(comp.getIntroduced()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }

            if (comp.getDiscontinued() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(comp.getDiscontinued()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            if (comp.getCompany() != null) {
                stmt.setLong(4, comp.getCompany().getId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

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
     * Update a computer into the database.
     *
     * @param comp
     *            the computer to update
     * @return the updated company
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public Computer update(Computer comp) {
        logger.info("UPDATE" + " << " + comp.toString());
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE);
            stmt.setString(1, comp.getName());

            if (comp.getIntroduced() != null) {
                stmt.setDate(2, java.sql.Date.valueOf(comp.getIntroduced()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }

            if (comp.getDiscontinued() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(comp.getDiscontinued()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            if (comp.getCompany() != null) {
                stmt.setLong(4, comp.getCompany().getId());
            }

            else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.setLong(5, comp.getId());
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
     * Delete a computer from the database
     *
     * @param comp
     *            the computer to delete
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public void delete(Computer comp) {
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
     * Wrapper function returning the entity
     *
     * @param rs
     *            : ResultSet receive by the database request
     * @return The created object (null if ResultSet error)
     */
    private Computer wrapDatabaseResult(ResultSet rs) throws SQLException {
        Computer cmp = null;
        // If result found, Computer created from the Database result
        if (rs.next()) {
            cmp = new Computer();
            cmp.setId(rs.getLong(1));
            cmp.setName(rs.getString(2));
            Timestamp dateIntroduced = rs.getTimestamp(3);
            if (dateIntroduced != null) {
                cmp.setIntroduced(dateIntroduced.toLocalDateTime().toLocalDate());
            }
            Timestamp datediscontinued = rs.getTimestamp(4);
            if (datediscontinued != null) {
                cmp.setDiscontinued(datediscontinued.toLocalDateTime().toLocalDate());
            }
            Company company = null;
            Long companyId = rs.getLong(5);
            if (companyId != null) {
                company = new Company();
                company.setId(companyId);
                String companyName = rs.getString(6);
                company.setName(companyName);
            }
            cmp.setCompany(company);
        }
        return cmp;
    }

    /**
     * List of the computers
     *
     * @return the list of all the computers
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    @Override
    public List<Computer> listAll() {
        logger.info("LISTALL");
        ResultSet results = null;
        List<Computer> computers = new ArrayList<Computer>();
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            Statement stmt = con.createStatement();
            results = stmt.executeQuery(LISTALL);

            Computer c;
            while ((c = wrapDatabaseResult(results)) != null) {
                computers.add(c);
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
        return computers;
    }

    /**
     * List of the computers from an indexed research
     *
     * @param begin
     *            the search index start
     * @param end
     *            the search index end
     * @return the list of all the computers
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public List<Computer> listAll(long begin, long end) {
        logger.info("LISTALL_INDEX" + " << " + begin + ", " + end);
        ResultSet results = null;
        List<Computer> computers = new ArrayList<Computer>();
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(LISTALL_INDEX);
            stmt.setLong(1, begin);
            stmt.setLong(2, end);
            results = stmt.executeQuery();

            Computer c;
            while ((c = wrapDatabaseResult(results)) != null) {
                computers.add(c);
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
        return computers;
    }

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
    public List<Computer> listAll(String regex, long begin, long end) {
        logger.info("LISTALL_INDEX_REGEX" + " << " + regex + begin + ", " + end);
        ResultSet results = null;
        List<Computer> computers = new ArrayList<Computer>();
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(LISTALL_INDEX_REGEX);
            stmt.setString(1, "%"+regex+"%");
            stmt.setLong(2, begin);
            stmt.setLong(3, end);
            results = stmt.executeQuery();

            Computer c;
            while ((c = wrapDatabaseResult(results)) != null) {
                computers.add(c);
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
        return computers;
    }

    /**
     * Count the computers.
     *
     * @return number of computers
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

    /**
     * Count the computers.
     *
     * @param regex The regular expression used for the counting
     * @return number of computers
     * @throws DAOException
     *             exception raised by connection or wrapper errors
     */
    public long count(String regex) {
        logger.info("COUNT" + regex);
        ResultSet results = null;
        long count = 0;
        Connection con = null;
        try {
            con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(COUNT_REGEX);
            stmt.setString(1, "%"+regex+"%");
            results = stmt.executeQuery();

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
