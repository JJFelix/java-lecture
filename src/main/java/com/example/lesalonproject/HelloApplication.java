package com.example.lesalonproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.Parent;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
//    public void start(Stage stage) throws Exception{
//        Parent root =FXMLLoader.load(getClass().getResource("student-view.fxml"));
//        stage.setTitle("Student Management");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }

    public static void main(String[] args) {
        launch();
    }
}