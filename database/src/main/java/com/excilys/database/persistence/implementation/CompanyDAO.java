package com.excilys.database.persistence.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.database.entities.Company;
import com.excilys.database.persistence.CompanyDaoInterface;
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

    @PersistenceContext
    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;
    // protected CriteriaQuery<Company> criteriaQuery;
    // protected CriteriaUpdate<Company> criteriaUpdate;
    // protected Root<Company> companyRoot;
    // protected Root<Company> companyRootUpdate;

    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");

    public CompanyDAO() {
    }

    @PostConstruct
    public void postConstruct() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        // criteriaQuery = criteriaBuilder.createQuery(Company.class);
        // companyRoot = criteriaQuery.from(Company.class);
        // criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Company.class);
        // companyRootUpdate = criteriaUpdate.from(Company.class);
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
    public Company find(Long id) {
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
        criteriaQuery.select(companyRoot)
        .where(criteriaBuilder.equal(companyRoot.get("name"), name));

        TypedQuery<Company> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Company create(Company comp) {
        logger.info("CREATE" + " << " + comp.toString());

        this.entityManager.persist(comp);
        this.entityManager.flush();
        this.entityManager.clear();
        return comp;
    }

    @Override
    @Transactional
    public Company update(Company comp) {
        logger.info("CREATE" + " << " + comp.toString());
        CriteriaUpdate<Company> criteriaUpdate = criteriaBuilder
                .createCriteriaUpdate(Company.class);
        System.out.println(comp.toString());
        /*
         * NOTE error here for tests due to JPA bug :
         * http://stackoverflow.com/questions/3854687/jpa-hibernate-static-metamodel-attributes-not-
         * populated-nullpointerexception
         *
         */
        criteriaUpdate.set("name", comp.getName());
        System.out.println("it works");
        int res = entityManager.createQuery(criteriaUpdate).executeUpdate();
        if (res == 0) {
            logger.error("Error update for: " + " << " + comp.toString());
            return null;
        } else {
            return comp;
        }
    }

    @Override
    @Transactional
    public void delete(Company comp) {
        logger.info("DELETE con" + " << " + comp.toString());

        CriteriaDelete<Company> delete = criteriaBuilder.createCriteriaDelete(Company.class);
        Root<Company> e = delete.from(Company.class);
        delete.where(criteriaBuilder.equal(e.get("id"), comp.getId()));
        int res = this.entityManager.createQuery(delete).executeUpdate();

        if (res == 0) {
            System.err.println("Computer not deleted :" + comp.toString());
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
