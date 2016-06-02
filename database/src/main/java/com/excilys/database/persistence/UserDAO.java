package com.excilys.database.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.excilys.database.entities.role.User;

@Repository
public class UserDAO {

    @PersistenceContext
    protected EntityManager entityManager;


    public User findByLogin(String login) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder
                .createQuery(User.class);
        Root<User> companyRoot = criteriaQuery.from(User.class);

        criteriaQuery.select(companyRoot)
        .where(criteriaBuilder.equal(companyRoot.get("login"), login));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }
}
