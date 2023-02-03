package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.example.model.User;

public class UserDao extends GenericDao<User> {

    private final EntityManagerFactory factory;

    public UserDao(EntityManagerFactory factory) {
        super(User.class);
        this.factory = factory;
    }

    @Override
    public EntityManager getEntityManager() {
        try {
            return factory.createEntityManager();
        } catch (Exception e) {
            System.out.println("The entity manager cannot be created");
            return null;
        }
    }

    public User loginUser(String username, String password) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);

        Root<User> c = q.from(User.class);
        ParameterExpression<String> userParamName = cb.parameter(String.class);
        q.select(c).where(cb.equal(c.get("username"), userParamName));

        TypedQuery<User> query = em.createQuery(q);
        query.setParameter(userParamName, username);

        User user = query.getSingleResult();
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}
