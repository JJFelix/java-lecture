package com.example.lesalonproject;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;

public class ParallelProgrammingController {

    @FXML
    private Label label1;  // Label that updates every 1 second

    @FXML
    private Label label2;  // Label that updates every 2 seconds

    // Method to start parallel updates when the button is clicked
    @FXML
    private void startParallelUpdates() {

        // Task for updating label1 every 1 second
        Task<Void> task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int count = 0;
                while (!isCancelled()) {
                    count++;
                    updateMessage("Label 1 Updated " + count + " times");
                    Thread.sleep(1000); // Wait for 1 second
                }
                return null;
            }
        };

        // Task for updating label2 every 2 seconds
        Task<Void> task2 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int count = 0;
                while (!isCancelled()) {
                    count++;
                    updateMessage("Label 2 Updated " + count + " times");
                    Thread.sleep(2000); // Wait for 2 seconds
                }
                return null;
            }
        };

        // Bind the text of the labels to the tasks' messages
        label1.textProperty().bind(task1.messageProperty());
        label2.textProperty().bind(task2.messageProperty());

        // Start the parallel tasks in separate threads
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.setDaemon(true);
        thread2.setDaemon(true);

        thread1.start();
        thread2.start();
    }
}

