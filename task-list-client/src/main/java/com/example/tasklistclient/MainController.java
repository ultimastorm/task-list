package com.example.tasklistclient;

import com.example.tasklistclient.controls.CreateTaskDialog;
import com.example.tasklistclient.controls.TaskItemCell;
import com.example.tasklistclient.model.TaskItem;
import com.example.tasklistclient.service.TaskItemService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML
    public ListView<TaskItem> taskItems;
    @FXML
    private Label welcomeText;

    @FXML
    void initialize() {
        loadTasks();
    }

    private void loadTasks() {
        List<TaskItem> items = TaskItemService.findAll();
        taskItems.setItems(FXCollections.observableArrayList(new ArrayList<>(items)));
        taskItems.setCellFactory(TaskItemCell::new);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onCreateTaskClicked() {
        CreateTaskDialog dialog = new CreateTaskDialog();

        Optional<TaskItem> result = dialog.showAndWait();
        result.ifPresentOrElse((task) -> {
                    System.out.println("Create task: " + task);
                    task = TaskItemService.create(task);
//                    loadTasks();
                    taskItems.getItems().add(task);
                },
                () -> System.out.println("Failed to create task")
        );
    }
}