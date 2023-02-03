package org.example;


import com.google.gson.Gson;
import org.example.model.LoginInput;
import org.example.model.TaskItem;
import org.example.model.User;
import org.example.service.TaskItemService;
import org.example.service.UserService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@FunctionalInterface
interface InputDataHandlerInterface<T> {

    // abstract method
    String executeTask(T inputData);
}

public class CommandService implements Runnable {
    private final ServerSocket serverSocket;
    // store one lambda for each command
    private static final HashMap<String, InputDataHandlerInterface<String>> map = new HashMap<String, InputDataHandlerInterface<String>>() {

        private static final long serialVersionUID = 1L;

        {
            put("task-item:create", (json) -> {
                TaskItem taskItem = new Gson().fromJson(json, TaskItem.class);
                TaskItemService ts = new TaskItemService();
                ts.create(taskItem);

                String returnJson = new Gson().toJson(taskItem);
                return returnJson;
            });
            put("task-item:findAll", (input) -> {
                long userId = Long.parseLong(input, 10);
                TaskItemService ts = new TaskItemService();
                List<TaskItem> items = ts.findAll(userId);
                System.out.println(items);
                String json = new Gson().toJson(items);
                System.out.println(json);
                return json;
            });
            put("task-item:remove", (json) -> {
                TaskItem taskItem = new Gson().fromJson(json, TaskItem.class);
                TaskItemService ts = new TaskItemService();
                if (taskItem.getId() != null) {
                    ts.remove(taskItem.getId());
                    return "Item removed.";
                }
                return "Failed to remove item.";
            });
            put("task-item:update", (json) -> {
                TaskItem taskItem = new Gson().fromJson(json, TaskItem.class);
                TaskItemService ts = new TaskItemService();
                ts.update(taskItem);

                String returnJson = new Gson().toJson(taskItem);
                return returnJson;
            });
            put("user:login", (json) -> {
                LoginInput input = new Gson().fromJson(json, LoginInput.class);
                UserService us = new UserService();
                User user = us.login(input.username, input.password);

                String returnJson = new Gson().toJson(user);
                return returnJson;
            });
        }
    };

    public CommandService(int port) throws IOException {
        // Create server socket and set the timeout for serverSocket.accept method
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(250);
    }

    @Override
    public void run() {
        try {
            accept();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void accept() throws IOException {
        System.out.println("Accepting connections on port " + serverSocket.getLocalPort());

        // Loop until the thread is interrupted
        while (!Thread.interrupted()) {
            // Use a try-with resources to instantiate the client socket and
            // buffers for reading and writing messages from and to the client
            try (Socket socket = serverSocket.accept();

                 BufferedWriter bufferedOutputWriter = new BufferedWriter(
                         new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader bufferedInputReader = new BufferedReader(
                         new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));) {

                System.out.println("Connection accepted");

                // Read the command and data from the client
                String receivedCommand = bufferedInputReader.readLine();
                String receivedData = bufferedInputReader.readLine();

                System.out.println("Command received:  " + receivedCommand);
                System.out.println("Data received:  " + receivedData);

                InputDataHandlerInterface<String> handler = map.get(receivedCommand);
//                String result = map.get(receivedCommand).executeTask(receivedData);
                if (handler == null) {
                    System.out.println(receivedCommand + " is not a valid command");
                } else {
                    String result = handler.executeTask(receivedData);

                    if (!result.isEmpty()) {
                        System.out.println("command: " + receivedCommand);
                        System.out.println("result: " + result);
                    } else {
                        System.out.println("Unexpected command");
                    }
                    outputToClient(bufferedOutputWriter, result, true);
                }

            } catch (SocketTimeoutException ste) {
                // timeout every .25 seconds to see if interrupted
            }
        }

        System.out.println("server connection end");
    }

    // Helper methods to send data to client
    private void outputToClient(BufferedWriter bufferedOutputWriter, String message, boolean withNewLine)
            throws IOException {

        bufferedOutputWriter.write(message);

        if (withNewLine) {
            bufferedOutputWriter.newLine();
        }

        bufferedOutputWriter.flush();
    }
}
