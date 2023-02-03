package com.example.tasklistclient.controllers;

import com.example.tasklistclient.TaskListApplication;
import com.example.tasklistclient.controls.CreateTaskDialog;
import com.example.tasklistclient.controls.TaskItemCell;
import com.example.tasklistclient.model.TaskItem;
import com.example.tasklistclient.model.User;
import com.example.tasklistclient.service.TaskItemService;
import com.example.tasklistclient.service.UsersService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML
    public ListView<TaskItem> taskItems;
    @FXML
    private Label welcomeText;

    @FXML
    private Button logoutButton;

    @FXML
    void initialize() {
        loadTasks();
        UsersService us = UsersService.getInstance();
        welcomeText.setText("Welcome, " + us.getCurrentUser().getFullName());
    }

    private void loadTasks() {
        UsersService us = UsersService.getInstance();
        List<TaskItem> items = TaskItemService.findAll(us.getCurrentUser().getId());
        taskItems.setItems(FXCollections.observableArrayList(new ArrayList<>(items)));
        taskItems.setCellFactory(TaskItemCell::new);
    }

    @FXML
    private void onCreateTaskClicked() {
        CreateTaskDialog dialog = new CreateTaskDialog();

        Optional<TaskItem> result = dialog.showAndWait();
        result.ifPresentOrElse((task) -> {
                    System.out.println("Create task: " + task);
                    task = TaskItemService.create(task);
                    taskItems.getItems().add(task);
                },
                () -> System.out.println("Failed to create task")
        );
    }

    @FXML
    private void onLogoutClicked() {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to log out?"
        );

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent((b) -> {
            if (b.getButtonData().isDefaultButton()) {
                Stage loginStage = (Stage) logoutButton.getScene().getWindow();
                loginStage.close();

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(TaskListApplication.class.getResource("login-view.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Task List!");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}