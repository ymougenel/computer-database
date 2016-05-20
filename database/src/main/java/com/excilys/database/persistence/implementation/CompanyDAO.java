package com.excilys.database.persistence.implementation;

import static com.excilys.database.persistence.DatabaseConnection.closePipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDaoInterface;
import com.excilys.database.persistence.DAOException;
/**
 * Company DAO (Singleton) Provides CRUD company database methods : Create, Retrieve, Update, Delete
 *
 * @author Yann Mougenel
 *
 */
@Repository
public class CompanyDAO implements CompanyDaoInterface {

    @Resource
    private DriverManagerDataSource dataSource;

    private static final String FIND_ID = "SELECT id, name from company WHERE id = ?;";
    private static final String FIND_NAME = "SELECT id, name from company WHERE name = ?;";
    private static final String CREATE = "INSERT INTO company (name) VALUES (?);";
    private static final String UPDATE = "UPDATE company SET name= ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM company WHERE id = ?;";
    private static final String LISTALL = "SELECT id,name from company;";
    private static final String COUNT = "SELECT COUNT(*) FROM company;";
    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    public CompanyDAO() {
    }

    @Override
    public Company find(long id) {
        logger.info("FIND_ID" + " << " + id);
        Company cmp;
        ResultSet results = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(FIND_ID);
            stmt.setLong(1, id);
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
    public Company find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));
        Company cmp;
        ResultSet results = null;
        // System.out.println("### +i query called for : "+query +" << "+name);
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
            closePipe(con);
            closePipe(results);
        }

        return cmp;
    }


    @Override
    public Company create(Company comp) {
        logger.info("CREATE" + " << " + comp.toString());
        ResultSet generatedKeys = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
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
            if (generatedKeys != null) {
                closePipe(generatedKeys);
            }
            closePipe(con);
        }
        return comp;
    }


    @Override
    public Company update(Company comp) {
        logger.info("UPDATE" + " << " + comp.toString());
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE);
            stmt.setString(1, comp.getName());
            stmt.setLong(2, comp.getId());
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
    public void delete(Company comp) {
        logger.info("DELETE con" + " << " + comp.toString());
        try {
            Connection con = this.dataSource.getConnection();
            PreparedStatement deleteCompany = con.prepareStatement(DELETE);
            deleteCompany.setLong(1, comp.getId());
            deleteCompany.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
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


    @Override
    public List<Company> listAll() {
        logger.info("LISTALL");
        ResultSet results = null;
        List<Company> companies = new ArrayList<Company>();
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
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
            closePipe(con);
            closePipe(results);
        }
        return companies;
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
}
