package com.example.lesalonproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class OpsystemCreate{

    @FXML
    private TextField osnameField;

    @FXML
    private void addOpsystem() {
        try {
            // Get data from the field
            String osname = osnameField.getText();

            // Call OpsystemDAO to save the operating system data
            OpsystemDAO.addOpsystem(osname);

            // Clear the field after successful insertion
            clearFields();

            showSuccess("Operating System added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showError("An error occurred while adding the operating system.");
        }
    }

    private void clearFields() {
        osnameField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Successful");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
