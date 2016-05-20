package com.excilys.database.persistence.implementation;

import static com.excilys.database.persistence.DatabaseConnection.closePipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.database.entities.Company;
import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.persistence.ComputerDaoInterface;
import com.excilys.database.persistence.DAOException;

/**
 * Computer DAO (Singleton) Provides CRUD computer database methods : Create, Retrieve, Update,
 * Delete
 *
 * @author Yann Mougenel
 *
 */
@Repository
public class ComputerDAO implements ComputerDaoInterface {

    @Resource
    private DataSource dataSource;

    private static final String FIND_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.id = ?;";
    private static final String FIND_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.name = ?;";
    private static final String CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
    private static final String UPDATE = "UPDATE computer SET name= ?, introduced= ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM computer WHERE id = ?;";
    private static final String LISTALL = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id;";
    private static final String LISTALL_INDEX = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id ORDER BY %s LIMIT ?,?;";
    private static final String LISTALL_INDEX_REGEX = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.name LIKE ? ORDER BY %s LIMIT ?,?;";
    private static final String COUNT = "SELECT COUNT(*) FROM computer;";
    private static final String COUNT_REGEX = "SELECT COUNT(*) FROM computer WHERE name LIKE ?;";

    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    public ComputerDAO() {
    }

    @Override
    public Computer find(long id) {
        logger.info("FIND_ID" + " << " + id);
        Computer cmp = null;
        ResultSet results = null;
        // System.out.println("### +i query called for : "+query +" << "+name);
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(FIND_ID);
            stmt.setLong(1, id);
            results = stmt.executeQuery();
            cmp = wrapDatabaseResult(results);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw new DAOException(e);
        } finally {
            closePipe(results);
            closePipe(con);
        }
        return cmp;
    }

    @Override
    public Computer find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));
        Computer cmp;
        ResultSet results = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(FIND_NAME);
            stmt.setString(1, name);
            results = stmt.executeQuery();
            cmp = wrapDatabaseResult(results);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closePipe(results);
            closePipe(con);
        }
        return cmp;
    }

    @Override
    public Computer create(Computer comp) {
        logger.info("CREATE" + " << " + comp.toString());
        Connection con = null;
        ResultSet generatedKeys = null;
        try {
            con = this.dataSource.getConnection();
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
            if (generatedKeys != null) {
                closePipe(generatedKeys);
            }
            closePipe(con);
        }
        return comp;
    }

    @Override
    public Computer update(Computer comp) {
        logger.info("UPDATE" + " << " + comp.toString());
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
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
            closePipe(con);
        }

        return comp;
    }

    @Override
    public void delete(Computer comp) {
        logger.info("DELETE" + " << " + comp.toString());
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(DELETE);
            stmt.setLong(1, comp.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closePipe(con);
        }
    }

    @Override
    public void delete(Long idCompany) {
        logger.info("DELETE ID Company " + " << " + idCompany);
        try {
            // Deleting related computers
            Connection con = this.dataSource.getConnection();
            PreparedStatement deleteComputer = con.prepareStatement("DELETE FROM computer where company_id = ?");
            deleteComputer.setLong(1, idCompany);
            deleteComputer.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
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

    @Override
    public List<Computer> listAll() {
        logger.info("LISTALL");
        ResultSet results = null;
        List<Computer> computers = new ArrayList<Computer>();
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
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
            closePipe(results);
            closePipe(con);
        }
        return computers;
    }

    @Override
    public List<Computer> listAll(String regex, long begin, long end, Page.CompanyTable field,
            Page.Order order) {
        logger.info("LISTALL_INDEX_REGEX" + " << " + regex + begin + ", " + end);
        ResultSet results = null;
        List<Computer> computers = new ArrayList<Computer>();
        Connection con = null;
        try {
            // Getting the connection and preparing the request
            con = this.dataSource.getConnection();
            PreparedStatement stmt;
            if (regex != null && !regex.isEmpty()) {
                stmt = con.prepareStatement(
                        String.format(LISTALL_INDEX_REGEX, field + " " + order.name()));
                stmt.setString(1,regex + "%");
                stmt.setLong(2, begin);
                stmt.setLong(3, end);
            } else {
                stmt = con
                        .prepareStatement(String.format(LISTALL_INDEX, field + " " + order.name()));
                stmt.setLong(1, begin);
                stmt.setLong(2, end);
            }

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
            closePipe(results);
            closePipe(con);
        }
        return computers;
    }

    public List<Computer> listFromCompany(String regex, long begin, long end, Page.CompanyTable field,
            Page.Order order) {
        logger.info("LISTALL_INDEX_REGEX" + " << " + regex + begin + ", " + end);
        List<Computer> computers = new ArrayList<Computer>();
        // TODO search where company.name LIKE regex
        // add it to the searched result (before or after ?), it implies changing the second DAO listing's indexes
        return computers;
    }

    @Override
    public long count() {
        logger.info("COUNT");
        ResultSet results = null;
        long count = 0;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
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
            closePipe(results);
            closePipe(con);
        }
        return count;
    }

    @Override
    public long count(String regex) {
        logger.info("COUNT" + regex);
        ResultSet results = null;
        long count = 0;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(COUNT_REGEX);
            stmt.setString(1, regex + "%");
            results = stmt.executeQuery();

            if (results.next()) {
                count = results.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        } finally {
            closePipe(results);
            closePipe(con);
        }
        return count;
    }
}
