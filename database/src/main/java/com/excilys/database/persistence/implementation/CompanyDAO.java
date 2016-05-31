package com.excilys.database.persistence.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @PersistenceContext
    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;
    //    protected CriteriaQuery<Company> criteriaQuery;
    //    protected CriteriaUpdate<Company> criteriaUpdate;
    //    protected Root<Company> companyRoot;
    //    protected Root<Company> companyRootUpdate;


    private static final String FIND_ID = "SELECT id, name from company WHERE id = ?;";
    private static final String FIND_NAME = "SELECT id, name from company WHERE name = ?;";
    private static final String UPDATE = "UPDATE company SET name= ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM company WHERE id = ?;";
    private static final String LISTALL = "SELECT id,name from company;";
    private static final String COUNT = "SELECT COUNT(*) FROM company;";
    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    public CompanyDAO() {
    }

    @PostConstruct
    public void postConstruct() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        //criteriaQuery = criteriaBuilder.createQuery(Company.class);
        //companyRoot = criteriaQuery.from(Company.class);
        //criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Company.class);
        //companyRootUpdate = criteriaUpdate.from(Company.class);
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> companyRoot = criteriaQuery.from(Company.class);
        criteriaQuery.select(companyRoot).where(criteriaBuilder.equal(companyRoot.get("id"), id));

        TypedQuery<Company> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Company find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));

        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> companyRoot = criteriaQuery.from(Company.class);
        //Root<Company> c =criteriaQuery.from(Company.class);
        criteriaQuery.select(companyRoot).where(criteriaBuilder.equal(companyRoot.get("name"), name));

        TypedQuery<Company> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
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
        logger.info("CREATE" + " << " + comp.toString());
        CriteriaUpdate<Company> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Company.class);
        System.out.println(comp.toString());
        criteriaUpdate.set("name", "tt");
        System.out.println("it works");
        int res = entityManager.createQuery(criteriaUpdate).executeUpdate();
        if (res==0) {
            logger.error("Error update for: " + " << " + comp.toString());
            return null;
        }
        else {
            return comp;
        }
    }

    @Override
    public void delete(Company comp) {
        logger.info("DELETE con" + " << " + comp.toString());

        CriteriaDelete<Company> delete = criteriaBuilder
                .createCriteriaDelete(Company.class);
        Root<Company> e = delete.from(Company.class);
        delete.where(criteriaBuilder.equal(e.get("id"), comp.getId()));
        int res = this.entityManager.createQuery(delete).executeUpdate();

        if (res == 0) {
            System.err.println("Computer not deleted :"+comp.toString());
        }
    }

    @Override
    public List<Company> listAll() {
        logger.info("LISTALL");
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> companyRoot = criteriaQuery.from(Company.class);
        criteriaQuery.select(companyRoot);
        TypedQuery<Company> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public long count() {
        logger.info("COUNT");
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(Company.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
