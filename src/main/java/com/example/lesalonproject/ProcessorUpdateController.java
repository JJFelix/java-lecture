package com.example.lesalonproject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ProcessorUpdateController {
    @FXML
    private ComboBox<Integer> idBox;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField typeField;

    public void initialize() throws SQLException {
        // Populate ComboBox with available Processor IDs
        try{
            idBox.getItems().addAll(ProcessorDAO.getAllProcessorsIds());
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error");
        }

    }

    @FXML
    private void loadProcessorData(){
        try{
            int selectedId = idBox.getValue();
            Processor processor = ProcessorDAO.getProcessorById(selectedId);

            //prefill fields with current data
            manufacturerField.setText(processor.getManufacturer());
            typeField.setText(processor.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateProcessor(){
        try{
            int id = idBox.getValue();
            String manufacturer = manufacturerField.getText();
            String type = typeField.getText();

            //Update notebook record
            ProcessorDAO.updateProcessor(id, manufacturer, type);
            clearFields();
//            System.out.println("Notebook added successfully");
            showSuccess("Notebook added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Error updating notebookk");
            showError("Error updating notebook");
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


    private void clearFields(){
        manufacturerField.clear();
        typeField.clear();
    }


    
}
