package com.example.tasklistclient.service;

import com.example.tasklistclient.model.LoginInput;
import com.example.tasklistclient.model.User;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class UsersService {

    private User currentUser = null;

    private static UsersService instance = null;

    private UsersService() {
    }

    public static UsersService getInstance() {
        if (instance == null) {
            instance = new UsersService();
        }

        return instance;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean login(String username, String password) {
        LoginInput input = new LoginInput();
        input.username = username;
        input.password = password;

        User user = processLogin(input);
        if (user != null) {
            currentUser = user;
            return true;
        } else {
            currentUser = null;
            return false;
        }
    }

    private User processLogin(LoginInput input) {
        ExecutorService es = Executors.newCachedThreadPool();

        String payload = new Gson().toJson(input);
        SocketClientCallable commandWithSocket = new SocketClientCallable("user:login", payload);

        Future<String> response = es.submit(commandWithSocket);
        try {
            // Blocking this thread until the server responds
            String serverResponse = response.get();
            System.out.println("Response from server is : " + serverResponse);
            User user = new Gson().fromJson(serverResponse, User.class);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
