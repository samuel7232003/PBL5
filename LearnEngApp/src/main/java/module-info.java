module com.example.learnengapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;
    requires java.sql;

    opens com.example.learnengapp to javafx.fxml;
    exports com.example.learnengapp;

    opens com.example.learnengapp.controller to javafx.fxml;
    exports com.example.learnengapp.controller;
}