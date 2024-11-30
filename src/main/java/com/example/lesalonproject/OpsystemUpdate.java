package com.example.lesalonproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class OpsystemUpdate {
    @FXML
    private ComboBox<Integer> idBox;
    @FXML
    private TextField osnameField;

    public void initialize() throws SQLException {
        // Populate ComboBox with available Operating System IDs
        try {
            idBox.getItems().addAll(OpsystemDAO.getAllOpsystemIds());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading Operating System IDs.");
        }
    }

    @FXML
    private void loadOpsystemData() {
        try {
            int selectedId = idBox.getValue();
            Opsystem opsystem = OpsystemDAO.getOpsystemById(selectedId);

            // Pre-fill fields with current data
            osnameField.setText(opsystem.getOsname());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading Operating System data.");
        }
    }

    @FXML
    private void updateOpsystem() {
        try {
            int id = idBox.getValue();
            String osname = osnameField.getText();

            // Update the operating system record
            OpsystemDAO.updateOpsystem(id, osname);
            clearFields();
            showSuccess("Operating System updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error updating Operating System.");
        }
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

    private void clearFields() {
        osnameField.clear();
    }
}
