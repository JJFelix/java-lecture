package com.example.lesalonproject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ProcessorCreateController {

    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField typeField;

    @FXML
    private void addProcessor() {
        try {
            // Get data from fields (no need to handle id, as it's auto-generated)
            String manufacturer = manufacturerField.getText();
            String type = typeField.getText();

            // Call ProcessorDAO to save the processor data
            ProcessorDAO.addProcessor(manufacturer, type);

            // Clear the fields after successful insertion
            clearFields();

            showSuccess("Processor added successfully!");
        } catch ( Exception e) {
            e.printStackTrace();
            showError("An error occurred while adding the processor.");
        }
    }

    private void clearFields() {
        manufacturerField.clear();
        typeField.clear();
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
