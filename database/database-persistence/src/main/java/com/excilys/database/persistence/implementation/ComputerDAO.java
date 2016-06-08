package com.excilys.database.persistence.implementation;

import com.excilys.database.entities.Computer;
import com.excilys.database.entities.Page;
import com.excilys.database.entities.Page.Order;
import com.excilys.database.persistence.ComputerDaoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.util.List;

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

    @PersistenceContext
    protected EntityManager entityManager;

    protected CriteriaBuilder criteriaBuilder;

    private static Logger logger = LoggerFactory.getLogger("CompanyDAO");
    public ComputerDAO() {
    }

    @PostConstruct
    public void postConstruct() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        // criteriaQuery = criteriaBuilder.createQuery(Company.class);
        // companyRoot = criteriaQuery.from(Company.class);
        // criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Company.class);
        // companyRootUpdate = criteriaUpdate.from(Company.class);
    }

    @Override
    public Computer find(Long id) {
        logger.info("FIND_ID" + " << " + id);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> companyRoot = criteriaQuery.from(Computer.class);
        criteriaQuery.select(companyRoot).where(criteriaBuilder.equal(companyRoot.get("id"), id));

        TypedQuery<Computer> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Computer find(String name) {
        logger.info("FIND_NAME" + " << " + (name == null ? "NULL" : name));

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> companyRoot = criteriaQuery.from(Computer.class);
        criteriaQuery.select(companyRoot)
        .where(criteriaBuilder.equal(companyRoot.get("name"), name));

        TypedQuery<Computer> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Computer create(Computer comp) {
        logger.info("CREATE" + " << " + comp.toString());

        this.entityManager.persist(entityManager.merge(comp));
        this.entityManager.flush();
        this.entityManager.clear();
        return comp;
    }

    @Override
    public Computer update(Computer comp) {
        logger.info("UPDATE" + " << " + comp.toString());
        this.entityManager.persist(this.entityManager.merge(comp));
        return comp;
    }

    @Override
    public void delete(Computer comp) {
        logger.info("DELETE" + " << " + comp.toString());

        //entityManager.remove(entityManager.merge(comp));

        // entityManager.joinTransaction();
        CriteriaDelete<Computer> delete = criteriaBuilder
                .createCriteriaDelete(Computer.class);
        Root<Computer> e = delete.from(Computer.class);
        delete.where(criteriaBuilder.equal(e.get("id"), comp.getId()));
        int res = this.entityManager.createQuery(delete).executeUpdate();

        if (res == 0) {
            System.err.println("Computer not deleted :" + comp.toString());
        }
    }

    @Override
    public void delete(Long idCompany) {
        logger.info("DELETE ID Company " + " << " + idCompany);

        CriteriaDelete<Computer> delete = criteriaBuilder
                .createCriteriaDelete(Computer.class);
        Root<Computer> e = delete.from(Computer.class);
        delete.where(criteriaBuilder.equal(e.get("company"), idCompany));
        int res = this.entityManager.createQuery(delete).executeUpdate();

        if (res == 0) {
            System.err.println("Computer not deleted (company related id :"+idCompany);
        }
    }

    @Override
    public List<Computer> listAll() {
        logger.info("LISTALL");
        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> companyRoot = criteriaQuery.from(Computer.class);
        criteriaQuery.select(companyRoot);
        TypedQuery<Computer> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Computer> listAll(String regex, long begin, long end, Page.CompanyTable field,
            Page.Order order) {
        logger.info("LISTALL_INDEX_REGEX" + " << " + regex + begin + ", " + end);

        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> computerRoot = criteriaQuery.from(Computer.class);

        criteriaQuery.select(computerRoot);
        if (!(regex == null || regex.isEmpty())) {
            //TODO add company name matching
            criteriaQuery.where(criteriaBuilder.like(computerRoot.get("name"), regex + "%"));
        }
        if (order == Order.ASC) {
            criteriaQuery.orderBy(criteriaBuilder.asc(computerRoot.get(field.toString())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(computerRoot.get(field.toString())));
        }

        TypedQuery<Computer> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) begin).setMaxResults((int) end);

        return typedQuery.getResultList();
    }

    @Override
    public long count() {
        logger.info("COUNT");
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(Computer.class)));
       return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public long count(String regex) {
        logger.info("COUNT" + regex);
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Computer> computerRoot = criteriaQuery.from(Computer.class);
        criteriaQuery.select(criteriaBuilder.count(computerRoot)).where(criteriaBuilder.like(computerRoot.get("name"), regex + "%"));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
