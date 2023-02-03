package org.example.service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.dao.UserDao;
import org.example.model.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("TaskListUnit");
            userDao = new UserDao(factory);
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public User login(String username, String password) {
        return userDao.loginUser(username, password);
    }
}
