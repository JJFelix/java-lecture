package com.example.lesalonproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;

public class OpsystemDelete {
    @FXML
    private ComboBox<Integer> idBox;

    public void initialize() throws SQLException {
        try {
            // Populate ComboBox with available Operating System IDs
            idBox.getItems().addAll(OpsystemDAO.getAllOpsystemIds());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error");
        }
    }

    @FXML
    private void deleteOpsystem() {
        try {
            int id = idBox.getValue();
            OpsystemDAO.deleteOpsystem(id);  // Call the delete method from OpsystemDAO

            // Optionally, provide feedback to the user after the deletion
            showSuccess("Operating System deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error deleting Operating System");
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
}
