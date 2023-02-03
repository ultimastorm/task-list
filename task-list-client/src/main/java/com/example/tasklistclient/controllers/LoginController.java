package com.example.tasklistclient.controllers;

import com.example.tasklistclient.TaskListApplication;
import com.example.tasklistclient.service.UsersService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public TextField tfUsername;

    @FXML
    public TextField tfPassword;

    @FXML
    public Button loginButton;

    @FXML
    public Label message;

    @FXML
    protected void onLoginButtonClicked() {
        if (tfPassword.getText().isEmpty() || tfPassword.getText().isEmpty()) {
            return;
        }

        UsersService us = UsersService.getInstance();
        if (us.login(tfUsername.getText(), tfPassword.getText())) {
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(TaskListApplication.class.getResource("main-view.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Task List!");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            message.setText("Login failed. Try again.");
            message.setVisible(true);
        }
    }
}
