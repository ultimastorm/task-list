package com.example.tasklistclient.controls;

import com.example.tasklistclient.model.TaskItem;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class TaskItemCell extends ListCell<TaskItem> {

    private final ListView<TaskItem> listView;

    public TaskItemCell(ListView<TaskItem> listView) {
       this.listView = listView;
    }

    @Override
    protected void updateItem(TaskItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            TaskItemView view = new TaskItemView(listView);
            view.setInfo(item);
            setGraphic(view.getRootView());
        }
    }
}
