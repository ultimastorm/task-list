package com.example.tasklistclient.controls;

import com.example.tasklistclient.model.TaskItem;
import com.example.tasklistclient.model.TaskStatus;
import com.example.tasklistclient.service.UsersService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CreateTaskDialog extends Dialog<TaskItem> {

    private final TextField tfName = new TextField();
    private final TextArea taDescription = new TextArea();

    public CreateTaskDialog() {

        Label nameLabel = new Label("Name of the task");
        Label descriptionLabel = new Label("Description");

        VBox vbox = new VBox(
                nameLabel, tfName,
                descriptionLabel, taDescription
        );

        vbox.setSpacing( 10.0d );
        vbox.setPadding( new Insets(40.0d) );

        DialogPane dp = getDialogPane();

        setTitle( "Create new task" );
        setResultConverter( this::formResult );

        ButtonType bt = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dp.getButtonTypes().addAll( bt, ButtonType.CANCEL );
        dp.setContent( vbox );

        tfName.requestFocus();
        tfName.setFocusTraversable(true);
    }

    private TaskItem formResult(ButtonType bt) {
        TaskItem item = null;
        if( bt.getButtonData() == ButtonBar.ButtonData.OK_DONE ) {
            if (!tfName.getText().isEmpty()) {
               UsersService us = UsersService.getInstance();

               item  = new TaskItem();
               item.setName(tfName.getText());
               item.setDescription(taDescription.getText());
               item.setStatus(TaskStatus.OPEN);
               item.setUser(us.getCurrentUser());
            }
        }
        return item;
    }



}
