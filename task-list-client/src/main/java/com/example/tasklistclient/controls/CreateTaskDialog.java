package com.example.tasklistclient.controls;

import com.example.tasklistclient.model.TaskItem;
import com.example.tasklistclient.model.TaskStatus;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CreateTaskDialog extends Dialog<TaskItem> {

    private final TextField tfName = new TextField();
    private final TextField tfDescription = new TextField();

    public CreateTaskDialog() {

        Label nameLabel = new Label("Name of the task");
        Label descriptionLabel = new Label("Description");

        VBox vbox = new VBox(
                nameLabel, tfName,
                descriptionLabel, tfDescription
        );

        vbox.setSpacing( 10.0d );
        vbox.setPadding( new Insets(40.0d) );

        DialogPane dp = getDialogPane();

        setTitle( "Connection Info" );
        setResultConverter( this::formResult );

        ButtonType bt = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dp.getButtonTypes().addAll( bt, ButtonType.CANCEL );
        dp.setContent( vbox );

        tfName.requestFocus();
        tfName.setFocusTraversable(true);

//        init( initialData );
    }

//    private void init(ConnectionInfo ci) {
//        if (ci != null) {
//            tfHost.setText( ci.getHost() );
//            tfUser.setText( ci.getUsername() );
//            tfPassword.setText( ci.getPassword() );
//        }
//    }

    private TaskItem formResult(ButtonType bt) {
        TaskItem item = null;
        if( bt.getButtonData() == ButtonBar.ButtonData.OK_DONE ) {
            if (!tfName.getText().isEmpty()) {
               item  = new TaskItem();
               item.setName(tfName.getText());
               item.setDescription(tfDescription.getText());
               item.setStatus(TaskStatus.OPEN);
            }
        }
        return item;
    }



}
