package com.excilys.database.persistence.implementation;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String FIND_ID = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.id = ?;";
    private static final String FIND_NAME = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.name = ?;";
    //private static final String CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
    private static final String UPDATE = "UPDATE computer SET name= ?, introduced= ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM computer WHERE id = ?;";
    private static final String LISTALL = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id;";
    private static final String LISTALL_INDEX = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id ORDER BY %s LIMIT ?,?;";
    private static final String LISTALL_INDEX_REGEX = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id company_id, o.name company_name FROM computer c LEFT JOIN company o on c.company_id = o.id WHERE c.name LIKE ? OR o.name LIKE ? ORDER BY %s LIMIT ?,?;";
    private static final String COUNT = "SELECT COUNT(*) FROM computer;";
    private static final String COUNT_REGEX = "SELECT COUNT(*) FROM computer WHERE name LIKE ?;";

    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    public ComputerDAO() {
    }

    private static final class ComputerMapper implements RowMapper<Computer> {

        @Override
        public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Computer computer = new Computer();
            computer.setId(rs.getLong("id"));
            computer.setName(rs.getString("name"));
            Timestamp introduced = rs.getTimestamp("introduced");
            if (introduced != null) {
                computer.setIntroduced(introduced.toLocalDateTime().toLocalDate());
            }
            Timestamp discontinued = rs.getTimestamp("discontinued");
            if (discontinued != null) {
                computer.setDiscontinued(discontinued.toLocalDateTime().toLocalDate());
            }
            Long companyId = rs.getLong("company_id");
            if (companyId != null) {
                String companyName = rs.getString("company_name");
                computer.setCompany(new Company(companyId, companyName));
            }
            return computer;
        }
    }

    @Override
    public Computer find(long id) {
        logger.info("FIND_ID" + " << " + id);
        Computer cmp = null;
        System.out.println("### +i query called for : "+FIND_ID +" << "+id);
        try {

            cmp = this.jdbcTemplate.queryForObject(FIND_ID, new Object[]{id},
                    new ComputerMapper());
            System.out.println("Wazzaaaaaa"+cmp==null);
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
    public Computer find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));
        Computer cmp;
        try {
            cmp = this.jdbcTemplate.queryForObject(FIND_NAME, new Object[] { name },
                    new ComputerMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw new DAOException(e);
        }
        return cmp;
    }

    @Override
    public Computer create(Computer comp) {
        logger.info("CREATE" + " << " + comp.toString());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("computer").usingGeneratedKeyColumns("id")
                .usingColumns("name", "introduced", "discontinued",
                        "company_id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", comp.getName());
        parameters.put("introduced", comp.getIntroduced() == null ? null
                : Date.valueOf(comp.getIntroduced()));
        parameters.put("discontinued", comp.getDiscontinued() == null ? null
                : Date.valueOf(comp.getDiscontinued()));
        parameters.put("company_id",
                (comp.getCompany() == null) ? null : comp.getCompany().getId());

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
    public Computer update(Computer comp) {
        logger.info("UPDATE" + " << " + comp.toString());

        Object[] args = new Object[5];
        args[0] = comp.getName();
        args[1] = (comp.getIntroduced() == null ? null : Date.valueOf(comp.getIntroduced()));
        args[2] = (comp.getDiscontinued() == null ? null : Date.valueOf(comp.getDiscontinued()));
        args[3] = (comp.getCompany() == null ? null : comp.getCompany().getId());
        args[4] = comp.getId();

        try {
            this.jdbcTemplate.update(UPDATE, args);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
        return comp;

    }

    @Override
    public void delete(Computer comp) {
        logger.info("DELETE" + " << " + comp.toString());
        try {
            Object[] params = { comp.getId() };
            int[] types = {Types.BIGINT};
            this.jdbcTemplate.update(DELETE, params, types);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Long idCompany) {
        logger.info("DELETE ID Company " + " << " + idCompany);
        try {
            Object[] params = { idCompany };
            int[] types = {Types.BIGINT};
            this.jdbcTemplate.update("DELETE FROM computer where company_id = ?", params, types);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Computer> listAll() {
        logger.info("LISTALL");
        List<Computer> computers;
        try {
            computers = this.jdbcTemplate.query(LISTALL, new ComputerMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw new DAOException(e);
        }
        return computers;
    }

    @Override
    public List<Computer> listAll(String regex, long begin, long end, Page.CompanyTable field,
            Page.Order order) {
        logger.info("LISTALL_INDEX_REGEX" + " << " + regex + begin + ", " + end);
        List<Computer> computers;
        try {
            if (regex != null && !regex.isEmpty()) {
                computers = this.jdbcTemplate.query(
                        String.format(LISTALL_INDEX_REGEX, field + " " + order.name()),
                        new Object[] { regex + "%", regex + "%", begin, end }, new ComputerMapper());
                System.out.println("c---------->"+computers.size());
            } else {
                computers = this.jdbcTemplate.query(
                        String.format(LISTALL_INDEX, field + " " + order.name()),
                        new Object[] { begin, end }, new ComputerMapper());
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw new DAOException(e);
        }

        return computers;
    }

    public List<Computer> listFromCompany(String regex, long begin, long end,
            Page.CompanyTable field, Page.Order order) {
        logger.info("LISTALL_INDEX_REGEX" + " << " + regex + begin + ", " + end);
        List<Computer> computers = new ArrayList<Computer>();
        // TODO search where company.name LIKE regex
        // add it to the searched result (before or after ?), it implies changing the second DAO
        // listing's indexes
        return computers;
    }

    @Override
    public long count() {
        logger.info("COUNT");
        return this.jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public long count(String regex) {
        logger.info("COUNT" + regex);
        return jdbcTemplate.queryForObject(COUNT_REGEX, new String[] { regex + "%" }, Long.class);
    }
}
