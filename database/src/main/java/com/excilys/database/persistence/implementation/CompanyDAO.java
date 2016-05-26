package com.excilys.database.persistence.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String FIND_ID = "SELECT id, name from company WHERE id = ?;";
    private static final String FIND_NAME = "SELECT id, name from company WHERE name = ?;";
    private static final String UPDATE = "UPDATE company SET name= ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM company WHERE id = ?;";
    private static final String LISTALL = "SELECT id,name from company;";
    private static final String COUNT = "SELECT COUNT(*) FROM company;";
    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    public CompanyDAO() {
    }

    private static final class CompanyMapper implements RowMapper<Company> {

        @Override
        public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
            Company company = new Company();
            company.setId(rs.getLong("id"));
            company.setName(rs.getString("name"));
            return company;
        }
    }

    @Override
    public Company find(long id) {
        logger.info("FIND_ID" + " << " + id);
        Company cmp;
        try {
            cmp = this.jdbcTemplate.queryForObject(FIND_ID, new Object[] { id },
                    new CompanyMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw new DAOException(e);
        }
        return cmp;
    }

    @Override
    public Company find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));
        Company cmp;
        try {
            cmp = this.jdbcTemplate.queryForObject(FIND_NAME, new Object[] { name },
                    new CompanyMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw new DAOException(e);
        }
        return cmp;
    }

    @Override
    public Company create(Company comp) {
        logger.info("CREATE" + " << " + comp.toString());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("company")
                .usingGeneratedKeyColumns("id").usingColumns("name");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", comp.getName());
        try {
            long row = insert.executeAndReturnKey(parameters).longValue();
            comp.setId(row);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
        return comp;
    }

    @Override
    public Company update(Company comp) {
        logger.info("UPDATE" + " << " + comp.toString());
        try {
            this.jdbcTemplate.update(UPDATE, new Object[] { comp.getName(), comp.getId() });
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
        return comp;
    }

    @Override
    public void delete(Company comp) {
        logger.info("DELETE con" + " << " + comp.toString());
        try {
            Object[] params = { comp.getId() };
            int[] types = { Types.BIGINT };
            this.jdbcTemplate.update(DELETE, params, types);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> listAll() {
        logger.info("LISTALL");
        List<Company> companies = new ArrayList<Company>();
        try {
            companies = this.jdbcTemplate.query(LISTALL, new CompanyMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw new DAOException(e);
        }
        return companies;
    }

    @Override
    public long count() {
        logger.info("COUNT");
        return this.jdbcTemplate.queryForObject(COUNT, Long.class);
    }
}
