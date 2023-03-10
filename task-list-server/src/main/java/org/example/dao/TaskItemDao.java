package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.example.model.TaskItem;

import java.util.List;

public class TaskItemDao extends GenericDao<TaskItem> {

    private final EntityManagerFactory factory;

    public TaskItemDao(EntityManagerFactory factory) {
        super(TaskItem.class);
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

    public List<TaskItem> findAllByUserId(long userId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TaskItem> q = cb.createQuery(TaskItem.class);

        Root<TaskItem> c = q.from(TaskItem.class);
        ParameterExpression<Long> paramName = cb.parameter(Long.class);
        q.select(c).where(cb.equal(c.get("user").get("id"), paramName));
        TypedQuery<TaskItem> query = em.createQuery(q);
        query.setParameter(paramName, userId);

        List<TaskItem> results = query.getResultList();
        return results;
    }

}
