package com.example.tasklistclient.service;

import com.example.tasklistclient.SocketClientCallable;
import com.example.tasklistclient.model.TaskItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskItemService {

    public static TaskItem create(TaskItem item) {
        ExecutorService es = Executors.newCachedThreadPool();

        String payload = new Gson().toJson(item);
        SocketClientCallable commandWithSocket = new SocketClientCallable("task-item:create", payload);

        Future<String> response = es.submit(commandWithSocket);
        try {
            // Blocking this thread until the server responds
            String serverResponse = response.get();
            System.out.println("Response from server is : " + serverResponse);
            TaskItem createdItem = new Gson().fromJson(serverResponse, TaskItem.class);

            return createdItem;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void remove(TaskItem item) {
        ExecutorService es = Executors.newCachedThreadPool();

        String payload = new Gson().toJson(item);
        SocketClientCallable commandWithSocket = new SocketClientCallable("task-item:remove", payload);

        Future<String> response = es.submit(commandWithSocket);
        try {
            // Blocking this thread until the server responds
            String serverResponse = response.get();
            System.out.println("Response from server is : " + serverResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TaskItem update(TaskItem item) {
        ExecutorService es = Executors.newCachedThreadPool();

        String payload = new Gson().toJson(item);
        SocketClientCallable commandWithSocket = new SocketClientCallable("task-item:update", payload);

        Future<String> response = es.submit(commandWithSocket);
        try {
            // Blocking this thread until the server responds
            String serverResponse = response.get();
            System.out.println("Response from server is : " + serverResponse);
            TaskItem updatedItem = new Gson().fromJson(serverResponse, TaskItem.class);

            return updatedItem;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<TaskItem> findAll() {

        ExecutorService es = Executors.newCachedThreadPool();

        SocketClientCallable commandWithSocket = new SocketClientCallable("task-item:findAll", "");

        Future<String> response = es.submit(commandWithSocket);
        try {
            // Blocking this thread until the server responds
            String serverResponse = response.get();

            System.out.println("Response from server is : " + serverResponse);
            List<TaskItem> items = new Gson().fromJson(
                    serverResponse,
                    TypeToken.getParameterized(List.class, TaskItem.class).getType()
            );

            System.out.println("items: " + items);

            return items;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
