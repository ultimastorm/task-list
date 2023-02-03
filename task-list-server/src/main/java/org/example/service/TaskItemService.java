package org.example.service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.dao.TaskItemDao;
import org.example.model.TaskItem;

import java.util.List;

public class TaskItemService {


    private TaskItemDao taskItemDao;

    public TaskItemService() {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("TaskListUnit");
            taskItemDao = new TaskItemDao(factory);
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public void create(TaskItem newTaskItem) {
        taskItemDao.create(newTaskItem);
    }

    public void update(TaskItem updatedTaskItem) {
        taskItemDao.update(updatedTaskItem);
    }

    public TaskItem find(int id) {
        return taskItemDao.find(id);
    }

    public List<TaskItem> findAll() {
        return taskItemDao.findAll();
    }

    public void remove(long id) {
        taskItemDao.remove(id);
    }
}
