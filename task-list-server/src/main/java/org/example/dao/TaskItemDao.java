package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.model.TaskItem;

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
}
