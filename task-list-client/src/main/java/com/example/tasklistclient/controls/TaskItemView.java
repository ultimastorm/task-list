package com.example.tasklistclient.controls;

import com.example.tasklistclient.model.TaskItem;
import com.example.tasklistclient.model.TaskStatus;
import com.example.tasklistclient.service.TaskItemService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class TaskItemView {


    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label name;

    @FXML
    private Label description;

    @FXML
    private Label status;

    @FXML
    private Button doneButton;

    private TaskItem taskItem;
    private final ListView<TaskItem> listView;

    public TaskItemView(ListView<TaskItem> listView) {
        this.listView = listView;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "task-item-cell.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setInfo(TaskItem taskItem) {
        this.taskItem = taskItem;

        name.setText(taskItem.getName());
        description.setText(taskItem.getDescription());
        status.setText(taskItem.getStatus().name());

        if (taskItem.getStatus() == TaskStatus.OPEN) {
            doneButton.setText("Done");
        } else {
            doneButton.setText("Open");
        }
    }

    public AnchorPane getRootView() {
        return rootPane;
    }

    @FXML
    private void onDoneButton(ActionEvent evt) {
        int index = listView.getItems().indexOf(taskItem);

        if (taskItem.getStatus() == TaskStatus.OPEN) {
            taskItem.setStatus(TaskStatus.DONE);
        } else {
            taskItem.setStatus(TaskStatus.OPEN);
        }
        taskItem = TaskItemService.update(taskItem);
        listView.getItems().set(index, taskItem);
    }

    @FXML
    private void onRemoveClicked(ActionEvent evt) {
        // todo: add confirmation Alert
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove the task?"
                );

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent((b) -> {
            System.out.println(b);
            if (b.getButtonData().isDefaultButton()) {
                TaskItemService.remove(taskItem);
                listView.getItems().remove(taskItem);
            }
        });
    }
}
