module com.example.tasklistclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;

    exports com.example.tasklistclient.model;

    opens com.example.tasklistclient.controls to javafx.fxml;
    exports com.example.tasklistclient.controls;

    opens com.example.tasklistclient to javafx.fxml;
    exports com.example.tasklistclient;
    exports com.example.tasklistclient.controllers;
    opens com.example.tasklistclient.controllers to javafx.fxml;
    exports com.example.tasklistclient.service;
    opens com.example.tasklistclient.service to javafx.fxml;
    opens com.example.tasklistclient.model to com.google.gson, javafx.fxml;
}